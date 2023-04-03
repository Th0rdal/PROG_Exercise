package at.ac.fhcampuswien.fhmdb;

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
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
    public JFXComboBox<Integer> yearComboBox;

    @FXML
    public JFXComboBox<Double> ratingComboBox;

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


        //genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());    //add all Genres to the comboBox
        genreComboBox.getSelectionModel().select(Genre.NONE);
        movieListView.setItems(filteredList);

        //init yearComboBox
        yearComboBox.setPromptText("Filter by Release Year");

        //init ratingComboBox
        ratingComboBox.setPromptText("Filter by Rating");

        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> this.filterMovies());

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> this.reverseMovies());

    }

    public void initializeMovies(ObservableList<Movie> movieList) throws IOException {
        if (movieList == null) {
            observableMovies.setAll(Movie.initializeMovies());
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

    public void filterMovies() {
        Predicate<Movie> filterGenre = i -> true;
        if (genreComboBox.getValue() != Genre.NONE && genreComboBox.getValue() != null) {
            filterGenre = i -> i.getGenres().contains(genreComboBox.getValue());    //filter by genre
        }
        Predicate<Movie> filterTitle = i -> i.getTitle().toLowerCase().contains(searchField.getText().toLowerCase());
        Predicate<Movie> filterDescription = i -> i.getDescription().toLowerCase().contains(searchField.getText().toLowerCase());
        Predicate<Movie> queryFilter = filterTitle.or(filterDescription);
        Predicate<Movie> filter = queryFilter.and(filterGenre);
        filteredList.setPredicate(filter);
    }

}

class UnexpectedSortStateException extends RuntimeException {
    public UnexpectedSortStateException(String message) {
        super(message);
    }
}