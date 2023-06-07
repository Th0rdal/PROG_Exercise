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
import java.util.*;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomeControllerTest extends ApplicationTest {

    /*ObservableList<Movie> testList = FXCollections.observableArrayList(
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


    );*/

    HomeController homeController = null;
    Stage stage = null;
    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeControllerTest.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        this.homeController = fxmlLoader.getController();
        scene.getStylesheets().add(Objects.requireNonNull(HomeControllerTest.class.getResource("styles.css")).toExternalForm());
        this.stage = stage;
        this.stage.setTitle("FHMDb");
        this.stage.setScene(scene);
    }


    private static void waitForRunLater() throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);
        Platform.runLater(semaphore::release);
        semaphore.acquire();
    }
    //method test cases
    @Test
    void initializeMovies_fills_viewList_with_Movie_data_when_parameter_null() throws InterruptedException {
        //given
        ObservableList<Movie> listNull = FXCollections.observableArrayList();
        listNull.addAll(Movie.initializeMovies());
        // listNull.remove(0); Um zu schauen ob der Test klappt.

        //when
        Platform.runLater(()-> this.homeController.initializeMovies(null));
        HomeControllerTest.waitForRunLater();

        //then
        for(int i =0; i < listNull.size();i++) {
            assertEquals(this.homeController.observableMovies.get(i), listNull.get(i));
        }

    }

    @Test
    void initializeMovies_fills_viewList_with_moviesList_given_as_parameter() throws InterruptedException {
        //given
        ObservableList<Movie> movieListAsParameter = FXCollections.observableArrayList();
        movieListAsParameter.addAll(Movie.initializeMovies());

        //when
        Platform.runLater(()-> this.homeController.initializeMovies(movieListAsParameter));
        HomeControllerTest.waitForRunLater();

        //then
        for(int i =0; i < movieListAsParameter.size();i++) {
            assertEquals(this.homeController.observableMovies.get(i), movieListAsParameter.get(i));
        }

    }
    @Test
    void sortMovies_sorts_movies_ascending() throws InterruptedException {
        // given
        ObservableList<Movie> sortingListAscending = FXCollections.observableArrayList(); // Creates a empty list

        ObservableList<Movie> startList = FXCollections.observableArrayList();// Creates a empty list and fill it manually
        startList.add(this.testList.get(2));
        startList.add(this.testList.get(0));
        startList.add(this.testList.get(1));

        // when
        //Java Fx Code
        Platform.runLater(() -> {
            this.homeController.initializeMovies(startList);
            this.homeController.sortMovies();
        });
        HomeControllerTest.waitForRunLater();

        // then
        sortingListAscending.addAll(this.homeController.observableMovies);
        Assertions.assertIterableEquals(sortingListAscending,this.testList); //Compare the two list
    }

    @Test
    void reverseMovies_reverses_list_when_sortButton_pressed_asc_to_desc() throws InterruptedException {
        //GIVEN
        Platform.runLater(() -> {
            this.homeController.initializeMovies(this.testList);
            this.homeController.sortMovies();
        });
        HomeControllerTest.waitForRunLater();

        //WHEN
        Platform.runLater(() -> this.homeController.reverseMovies());
        HomeControllerTest.waitForRunLater();

        //THEN
        /*ObservableList<Movie> expected = FXCollections.observableArrayList(
            new Movie(
                    "Generic Movie",
                    "The most generic movie ever made.",
                    Arrays.asList(Genre.COMEDY, Genre.CRIME)
            ),
            new Movie(
                    "Death Race 2000",
                    "Race through the United States in the far distant future of the year 2000",
                    Arrays.asList(Genre.ADVENTURE, Genre.ACTION)
            ),
            new Movie(
                    "Bamboo House",
                    "Movie about the struggle of the chinese resistance in wwII.",
                    Arrays.asList(Genre.DRAMA, Genre.ROMANCE)
            ));*/
        Assertions.assertIterableEquals(expected, this.homeController.observableMovies);
    }

    @Test
    void reverseMovies_reverses_list_when_sortButton_pressed_desc_to_asc() throws InterruptedException {
        //GIVEN
        /*ObservableList<Movie> testList = FXCollections.observableArrayList(
            new Movie(
                "Generic Movie",
                "The most generic movie ever made.",
                Arrays.asList(Genre.COMEDY, Genre.CRIME)
        ),
            new Movie(
                "Death Race 2000",
                "Race through the United States in the far distant future of the year 2000",
                Arrays.asList(Genre.ADVENTURE, Genre.ACTION)
        ),
            new Movie(
                "Bamboo House",
                "Movie about the struggle of the chinese resistance in wwII.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)
        ));*/
        Platform.runLater(() -> this.homeController.initializeMovies(testList));
        HomeControllerTest.waitForRunLater();

        //WHEN
        Platform.runLater(() -> this.homeController.reverseMovies());
        HomeControllerTest.waitForRunLater();

        //THEN
        Assertions.assertIterableEquals(this.testList, this.homeController.observableMovies);
    }

    @Test
    void reverseMovies_throws_error_when_sortState_is_not_ASCENDING_or_DESCENDING() throws InterruptedException {
        //GIVEN
        Platform.runLater(() -> {
            this.homeController.initializeMovies(null);
            this.homeController.sortState = HomeController.SortState.NONE;
        });
        HomeControllerTest.waitForRunLater();

        //WHEN THEN
        Platform.runLater(() -> assertThrows(UnexpectedSortStateException.class, () -> this.homeController.reverseMovies()));
        HomeControllerTest.waitForRunLater();
    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_selected_genre() throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.homeController.initializeMovies(startList);
            this.homeController.genreComboBox.getSelectionModel().select(Genre.ACTION);
        });

        //WHEN
        Platform.runLater(() -> this.homeController.filterMovies());
        HomeControllerTest.waitForRunLater();

        //THEN
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(this.testList.get(1));
        Assertions.assertIterableEquals(expected, this.homeController.filteredList);
    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_textBox_term_in_title() throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.homeController.initializeMovies(startList);
            this.homeController.searchField.setText("Bamboo");
        });

        //WHEN
        Platform.runLater(() -> this.homeController.filterMovies());
        HomeControllerTest.waitForRunLater();

        //THEN
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(this.testList.get(0));
        Assertions.assertIterableEquals(expected, this.homeController.filteredList);
    }

    @Test
    void filterMovies_filters_out_all_movies_without_the_textBox_term_in_description() throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.homeController.initializeMovies(startList);
            this.homeController.searchField.setText("most");
        });

        //WHEN
        Platform.runLater(() -> this.homeController.filterMovies());
        HomeControllerTest.waitForRunLater();

        //THEN
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(this.testList.get(2));
        Assertions.assertIterableEquals(expected, this.homeController.filteredList);
    }

    @Test
    void filteredMovies_shows_whole_movieList_when_all_filters_are_removed() throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.homeController.initializeMovies(startList);
            this.homeController.searchField.setText("in");
            this.homeController.genreComboBox.getSelectionModel().select(Genre.ACTION);
            this.homeController.filterMovies();
        });

        //WHEN
        Platform.runLater(() -> {
            this.homeController.searchField.setText("");
            this.homeController.genreComboBox.getSelectionModel().select(Genre.NONE);
            this.homeController.filterMovies();
        });
        HomeControllerTest.waitForRunLater();

        //THEN
        Assertions.assertIterableEquals(startList, this.homeController.filteredList);
    }

    //JavaFx based test cases
    @Test
    void ui_reverses_when_sortBtn_is_pressed_asc_to_desc(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();

        //WHEN
        robot.clickOn("#sortBtn");

        //THEN
        FXCollections.reverse(startList);
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(startList, receivedList);
    }

    @Test
    void ui_reverses_when_sortBtn_is_pressed_desc_to_asc(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();
        robot.clickOn("#sortBtn");

        //WHEN
        robot.clickOn("#sortBtn");

        //THEN
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(startList, receivedList);
    }

    @Test
    void ui_filters_movies_when_genre_is_selected(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();

        //WHEN
        robot.clickOn("#genreComboBox");
        robot.moveBy(0.0, 100.0);
        robot.rightClickOn();
        robot.clickOn("#searchBtn");

        //THEN
        Genre selectedGenre = (Genre) robot.lookup("#genreComboBox").queryComboBox().getValue();
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        for (Movie movie: startList) {
            if (movie.getGenres().contains(selectedGenre)) {
                expected.add(movie);
            }
        }
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(expected, receivedList);
    }

    @Test
    void ui_filters_movies_when_searchString_is_entered(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();

        //WHEN
        robot.clickOn("#searchField");
        robot.write("Bamboo");
        robot.clickOn("#searchBtn");

        //THEN
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(this.testList.get(0));
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(expected, receivedList);
    }

    @Test
    void ui_filters_movies_when_searchString_is_entered_and_genre_selected(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();

        //WHEN
        robot.clickOn("#searchField");
        robot.write("in");
        robot.clickOn("#genreComboBox");
        robot.moveBy(0.0, 100.0);
        robot.rightClickOn();
        robot.clickOn("#searchBtn");

        //THEN
        ObservableList<Movie> expected = FXCollections.observableArrayList();
        expected.add(this.testList.get(1));
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(expected, receivedList);
    }

    @Test
    void ui_shows_all_movies_after_all_filters_are_removed(FxRobot robot) throws InterruptedException {
        //GIVEN
        ObservableList<Movie> startList = FXCollections.observableArrayList();
        startList.addAll(this.testList);
        Platform.runLater(() -> {
            this.stage.show();
            this.homeController.initializeMovies(startList);
        });
        HomeControllerTest.waitForRunLater();
        robot.clickOn("#searchField");
        robot.write("in");
        robot.clickOn("#genreComboBox");
        robot.moveBy(0.0, 100.0);
        robot.rightClickOn();
        robot.clickOn("#searchBtn");

        //WHEN
        robot.clickOn("#genreComboBox");
        robot.moveBy(0.0, 20.0);
        robot.rightClickOn();
        robot.clickOn("#searchField");
        robot.eraseText(2);
        robot.clickOn("#searchBtn");

        //THEN
        ObservableList<Object> receivedList = robot.lookup("#movieListView").queryListView().getItems();
        Assertions.assertIterableEquals(startList, receivedList);

    }
}

/*

*/