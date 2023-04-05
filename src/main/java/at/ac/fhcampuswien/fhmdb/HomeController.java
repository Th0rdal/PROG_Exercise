package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox<Genre> genreComboBox;

    @FXML
    public JFXComboBox<String> yearComboBox;

    @FXML
    public JFXComboBox<String> ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    public FilteredList<Movie> filteredList = new FilteredList<>(observableMovies, null);

    public enum SortState {
        NONE,
        ASCENDING,
        DESCENDING
    }

    public SortState sortState = SortState.NONE;
    public MovieAPI movieAPI = new MovieAPI();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //preparing the movie list
        try {
            this.initializeMovies(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sortMovies();

        // initialize UI stuff
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
        System.out.println(this.getMostPopularActor(observableMovies));
        System.out.println(this.getLongestMovieTitle(observableMovies));
        System.out.println(this.countMoviesFrom(observableMovies,"Peter Jackson"));
        System.out.println(this.getMoviesBetweenYears(observableMovies,2012,2011));



        //genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());    //add all Genres to the comboBox
        genreComboBox.getSelectionModel().select(Genre.NONE);
        movieListView.setItems(filteredList);

        //yearComboBox init
        yearComboBox.getSelectionModel().select("No release year filter");

        //ratingComboBox init
        ratingComboBox.getSelectionModel().select("No rating filter");
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> {
            try {
                observableMovies.setAll(this.movieAPI.getFilteredMovieList(searchField.getText(), genreComboBox.getValue(), yearComboBox.getValue(), ratingComboBox.getValue()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> this.reverseMovies());

    }

    public void initializeMovies(ObservableList<Movie> movieList) throws IOException {
        if (movieList == null) {
            observableMovies.setAll(Movie.initializeMovies());
            List<String> yearList = new ArrayList<>();
            observableMovies.stream().map(Movie::getYear).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(yearList::add);
            this.yearComboBox.getItems().setAll(yearList);
            this.yearComboBox.getItems().add("No release year filter");

            List<String> ratingList = new ArrayList<>();
            observableMovies.stream().map(Movie::getRating).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(ratingList::add);
            this.ratingComboBox.getItems().addAll(ratingList);
            this.ratingComboBox.getItems().add("No rating filter");
            return;
        }
        observableMovies.setAll(movieList);
    }

    public void sortMovies() {
        observableMovies.sort(Comparator.comparing(Movie::getTitle));   //sort the list ascending
        sortState = SortState.ASCENDING;
    }

    public void reverseMovies(){
        if (this.sortState == SortState.ASCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.DESCENDING;
            this.sortBtn.setText("Sort (desc)");
        }else if (this.sortState == SortState.DESCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.ASCENDING;
            this.sortBtn.setText("Sort (asc)");
        }else {
            throw new UnexpectedSortStateException("Error: sortState is \"" + sortState.toString() + "\"! Expected \"ASCENDING\" or \"DESCENDING\"!");
        }
    }

    public String getMostPopularActor(List<Movie> movies) {
        Map<String, Integer> mainCastMap = new HashMap<>();
        movies.stream().map(Movie::getMainCast).flatMap(List::stream).forEach(i -> {
            if (mainCastMap.containsKey(i)) {
                mainCastMap.put(i, mainCastMap.get(i)+1);
            }else {
                mainCastMap.put(i, 1);
            }
        });
        return mainCastMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream().map(Movie::getTitle).map(String::length).max(Integer::compareTo).get();

    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream()
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }


    /*public void filterMovies() {
        Predicate<Movie> filterGenre = i -> true;
        if (genreComboBox.getValue() != Genre.NONE && genreComboBox.getValue() != null) {
            filterGenre = i -> i.getGenres().contains(genreComboBox.getValue());    //filter by genre
        }
        Predicate<Movie> filterTitle = i -> i.getTitle().toLowerCase().contains(searchField.getText().toLowerCase());
        Predicate<Movie> filterDescription = i -> i.getDescription().toLowerCase().contains(searchField.getText().toLowerCase());
        Predicate<Movie> queryFilter = filterTitle.or(filterDescription);
        Predicate<Movie> filter = queryFilter.and(filterGenre);
        filteredList.setPredicate(filter);
    }*/

}

class UnexpectedSortStateException extends RuntimeException {
    public UnexpectedSortStateException(String message) {
        super(message);
    }
}