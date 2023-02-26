package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public enum SortState {
        NONE,
        ASCENDING,
        DESCENDING
    }

    public SortState sortState = SortState.NONE;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        //sorting list ascending
        observableMovies.sort(Comparator.comparing(Movie::getTitle));
        sortState = SortState.ASCENDING;


        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");

        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            observableMovies = this.reverseMovies(observableMovies);
        });

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