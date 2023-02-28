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

import java.net.URL;
import java.util.Comparator;
import java.util.List;
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
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

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
        observableMovies.addAll(allMovies); //adding movies to the list
        observableMovies.sort(Comparator.comparing(Movie::getTitle));   //sort the list ascending
        sortState = SortState.ASCENDING;

        // initialize UI stuff
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data


        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());    //add all Genres to the comboBox
        this.filterByGenre(filteredList, genreComboBox.getValue());
        movieListView.setItems(filteredList);

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here
        searchBtn.setOnAction(actionEvent -> {
            this.filterByGenre(filteredList, genreComboBox.getValue());
        });

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            observableMovies = this.reverseMovies(observableMovies);
        });

    }

    public void filterByGenre(FilteredList<Movie> filterList, Genre filterGenre) {
        Predicate<Movie> filter = null;
        if (filterGenre != Genre.NONE && filterGenre != null) {
            filter = i -> i.getGenres().contains(filterGenre);    //filter by genre
        }
        filterList.setPredicate(filter);
    }
    public void initializeState(){
        observableMovies.clear();
        observableMovies.addAll(allMovies);
    }

    public ObservableList<Movie> reverseMovies(ObservableList<Movie> listToSort){
        if (listToSort.size() == 0) {
            System.out.print("Error: list to sort is empty\n");
            return listToSort;
        }
        if (this.sortState == SortState.ASCENDING) {
            FXCollections.reverse(listToSort);
            this.sortState = SortState.DESCENDING;
            this.sortBtn.setText("Sort (desc)");
        }else if (this.sortState == SortState.DESCENDING) {
            FXCollections.reverse(listToSort);
            this.sortState = SortState.ASCENDING;
            this.sortBtn.setText("Sort (asc)");
        }else {
            System.out.println("Error: sortState is " + sortState.toString());
        }
        return listToSort;
    }
}