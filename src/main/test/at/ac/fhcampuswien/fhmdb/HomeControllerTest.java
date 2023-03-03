package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@ExtendWith(ApplicationExtension.class)
class HomeControllerTest extends ApplicationTest {

    ObservableList<Movie> testList = FXCollections.observableArrayList(
            new Movie(
                    "Bamboo House",
                    "Movie about the struggle of the chinese resistance in wwII.",
                    Arrays.asList(Genre.DRAMA, Genre.ROMANCE)
            ),

            new Movie(
                    "Death Race 2000",
                    "Race through the United States in the far distant future of the year 2000",
                    Arrays.asList(Genre.ADVENTURE, Genre.ACTION)
            ),

            new Movie(
                    "Generic Movie",
                    "The most generic movie ever made.",
                    Arrays.asList(Genre.COMEDY, Genre.CRIME)
            )
    );

    HomeController homeController = null;

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeControllerTest.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        this.homeController = fxmlLoader.getController();
        scene.getStylesheets().add(Objects.requireNonNull(HomeControllerTest.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();
    }


    //method test cases
    @Test
    void initializeMovies_fills_viewList_with_Movie_data_when_parameter_null() {
        //TODO write test case
    }

    @Test
    void initializeMovies_fills_viewList_with_moviesList_given_as_parameter() {
        //TODO write test case
    }
    @Test
    void sortMovies_sorts_movies_ascending() {
        //TODO write test case
    }

    @Test
    void reverseMovies_reverses_list_when_sortButton_pressed_asc_to_desc() {
        //TODO write test case
    }

    @Test
    void reverseMovies_reverses_list_when_sortButton_pressed_desc_to_asc() {
        //TODO write test case
    }

    @Test
    void reverseMovies_throws_error_when_sortState_is_not_ASCENDING_or_DESCENDING() {
        //TODO write test case
    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_selected_genre() {
        //TODO write test case

    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_textBox_term_in_title() {
        //TODO write test case
    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_textBox_term_in_description() {
        //TODO write test case
    }

    @Test
    void filterMovies_only_shows_filtered_movies() {
        //TODO write test case
    }

    @Test
    void filteredMovies_shows_whole_movieList_when_all_filters_are_removed() {
        //Todo write test case
    }

    //JavaFx based test cases
    @Test
    void ui_reverses_when_sortBtn_is_pressed_asc_to_desc(FxRobot robot){
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        Platform.runLater(() -> {
            startList.addAll(this.testList);
            this.homeController.initializeMovies(startList);
        });

        //WHEN
        robot.clickOn("#sortBtn");

        //THEN
        FXCollections.reverse(startList);
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(startList, receivedList);
    }

    @Test
    void ui_reverses_when_sortBtn_is_pressed_desc_to_asc(FxRobot robot){
        //GIVEN
        robot.clickOn("#sortBtn");
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        Platform.runLater(() -> {
            startList.addAll(this.testList);
            this.homeController.initializeMovies(startList);
        });

        //WHEN
        robot.clickOn("#sortBtn");

        //THEN
        FXCollections.reverse(startList);
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(startList, receivedList);
    }

    @Test
    void ui_filters_movies_when_genre_is_selected(FxRobot robot) {
        //TODO write test case
    }

    @Test
    void ui_filters_movies_when_searchString_is_entered(FxRobot robot) {
        //TODO write test case
    }

    @Test
    void ui_filters_movies_when_searchString_is_entered_and_genre_selected(FxRobot robot) {
        //TODO write test case
    }
}