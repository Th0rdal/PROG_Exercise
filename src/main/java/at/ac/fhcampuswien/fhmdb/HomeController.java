package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.api.MovieApiException;
import at.ac.fhcampuswien.fhmdb.database.DatabaseException;
import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
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
    public ObservableList<Movie> watchlistMovies = FXCollections.observableArrayList();
    public ObservableList<Movie> saveListMovies = FXCollections.observableArrayList();
    @FXML
    public JFXButton changeListButton;
    private final WatchlistRepository watchlistRepository = new WatchlistRepository();
    @FXML
    public AnchorPane anchorPane;
    public enum SortState {
        NONE,
        ASCENDING,
        DESCENDING
    }
    public enum ListState{
        NONE,
        WATCHLIST,
        APIMOVIELIST
    }
    private ListState listState = ListState.NONE;

    public SortState sortState = SortState.NONE;
    public MovieAPI movieAPI = new MovieAPI();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //preparing the movie list
        try {
            this.initializeMovies(null);
        } catch (IOException e) {
            new MovieApiException("Error in fetching the movies from the API. Check if you have internet connection!");
        }
        this.sortMovies();

        ClickEventHandler clickEventHandler = (clickedItem, homeController) -> {
            WatchlistRepository watchlistRepository = new WatchlistRepository();
            if (homeController.getListState() == ListState.APIMOVIELIST) {
                try {
                    watchlistRepository.addToWatchlist(WatchlistEntity.convertMovieToWatchlistEntity((Movie) clickedItem));
            } catch (SQLException e) {
                new DatabaseException("Error in trying to add a movie to the Watchlist");
            }
            }else if (homeController.getListState() == ListState.WATCHLIST) {
                try {
                    watchlistRepository.removeFromWatchlist(WatchlistEntity.convertMovieToWatchlistEntity((Movie) clickedItem));
                } catch (SQLException e) {
                    new DatabaseException("Error in trying to delete a movie from the Watchlist");
                }
                homeController.updateObservableMovieList();
            }

        };
        // initialize UI stuff
        movieListView.setCellFactory(movieListView -> new MovieCell(this, clickEventHandler)); // use custom cell factory to display data
        //System.out.println(this.getMostPopularActor(observableMovies));
        //System.out.println(this.getLongestMovieTitle(observableMovies));
        //System.out.println(this.countMoviesFrom(observableMovies,"Peter Jackson"));
        //this.getMoviesBetweenYears(observableMovies,2000,2050)
         //       .stream()
          //      .forEach(Movie::displayOnConsole);



        //genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());    //add all Genres to the comboBox
        genreComboBox.getSelectionModel().select(Genre.NONE);
        movieListView.setItems(this.observableMovies);

        //yearComboBox fill comboBox
        yearComboBox.getSelectionModel().select("No release year filter");
        ratingComboBox.getSelectionModel().select("No rating filter");

        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> {
            if (this.listState == ListState.APIMOVIELIST) {
                try {
                    observableMovies.setAll(this.movieAPI.getFilteredMovieList(searchField.getText(), genreComboBox.getValue(), yearComboBox.getValue(), ratingComboBox.getValue()));
                    observableMovies.forEach(Movie::displayOnConsole);
                    this.updateComboBoxes();
                } catch (IOException e) {
                    new MovieApiException("Error in fetching the movies from the API. Check if you have an internet connection!");
                }
            }else if (this.listState == ListState.WATCHLIST) {
                this.observableMovies.setAll(this.watchlistMovies.stream()
                        .filter(i -> i.getTitle().toLowerCase().contains(searchField.getText().toLowerCase()))
                        .filter(i -> i.getDescription().toLowerCase().contains(searchField.getText().toLowerCase()))
                        .filter(i -> {
                            if (genreComboBox.getValue() == Genre.NONE) {
                                return true;
                            }else return i.getGenres().contains(genreComboBox.getValue());
                        })
                        .filter(i -> {
                            if (yearComboBox.getValue().equals("No release year filter")) {
                                return true;
                            }
                            return Integer.parseInt(yearComboBox.getValue()) == i.getReleaseYear();
                        })
                        .filter(i -> {
                            if (ratingComboBox.getValue().equals("No rating filter")) {
                                return true;
                            }
                            return Double.parseDouble(ratingComboBox.getValue()) == i.getReleaseYear();
                        }).toList());
            }else {
                throw new UnexpectedListStateException("Reached an unexpected ListState!");
            }

        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> this.reverseMovies());

        changeListButton.setOnAction(actionEvent -> {
            if (this.listState == ListState.WATCHLIST) {
                this.listState = ListState.APIMOVIELIST;
                try {
                    this.observableMovies.setAll(Movie.initializeMovies());
                } catch (IOException e) {
                    new MovieApiException("Error in fetching the movies from the API. Using old data!");
                    this.observableMovies.setAll(this.saveListMovies);
                }
                this.updateComboBoxes();
                this.sortMovies();
                this.changeListButton.setText("Watchlist");
            }else {
                this.listState = ListState.WATCHLIST;
                this.saveListMovies.addAll(this.observableMovies);
                this.updateObservableMovieList();
                this.changeListButton.setText("Back");
            }

        });
    }

    public void updateObservableMovieList() {
        try {
            List<WatchlistEntity> temp = this.watchlistRepository.getAll();
            List<Movie> tempMovie = temp.stream().map(WatchlistEntity::convertWatchlistEntityToMovie).toList();
            this.watchlistMovies.setAll(tempMovie);
            this.observableMovies.setAll(this.watchlistMovies);
        } catch (SQLException e) {
            new DatabaseException("Error in fetching elements from the database");
        }
        this.updateComboBoxes();
        this.sortMovies();
    }

    public void initializeMovies(ObservableList<Movie> movieList) throws IOException {
        if (movieList == null) {
            observableMovies.setAll(Movie.initializeMovies());
            this.listState = ListState.APIMOVIELIST;
            this.updateComboBoxes();
            return;
        }
        observableMovies.setAll(movieList);
    }

    public void updateComboBoxes() {
        String currentValue = this.yearComboBox.getValue();
        if (currentValue == null || currentValue.equals("No release year filter")) {
            List<String> yearList = new ArrayList<>();
            observableMovies.stream().map(Movie::getReleaseYear).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(yearList::add);
            this.yearComboBox.getItems().setAll(yearList);
            this.yearComboBox.getItems().add("No release year filter");
            this.yearComboBox.getSelectionModel().select(currentValue);
        }

        String currentRating = this.ratingComboBox.getValue();
        if (currentRating == null || currentRating.equals("No rating filter")) {
            List<String> ratingList = new ArrayList<>();
            observableMovies.stream().map(Movie::getRating).distinct().sorted(Comparator.reverseOrder()).map(String::valueOf).forEach(ratingList::add);
            this.ratingComboBox.getItems().setAll(ratingList);
            this.ratingComboBox.getItems().add("No rating filter");
            this.ratingComboBox.getSelectionModel().select(currentRating);
        }
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
        movies.stream()
                .filter(Objects::nonNull)
                .map(Movie::getMainCast)
                .flatMap(List::stream)
                .forEach(i -> {
                    if (mainCastMap.containsKey(i)) {
                        mainCastMap.put(i, mainCastMap.get(i)+1);
                    }else {
                        mainCastMap.put(i, 1);
                    }
                });
        return mainCastMap
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .filter(Objects::nonNull)
                .map(Movie::getTitle)
                .map(String::length)
                .max(Integer::compareTo)
                .get();

    }

    public long countMoviesFrom(List<Movie> movies, String director){
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getDirectors().contains(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    public ListState getListState() {return this.listState;}


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
