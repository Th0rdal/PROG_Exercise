package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;

    @BeforeAll
    static void inti(){ homeController = new HomeController(); }

    @Test
    void movies_and_observableMovies_are_equal(){

        //GIVEN
        homeController.initializeState();

        //WHEN + THEN
        assertEquals(homeController.allMovies, homeController.observableMovies);
    }

    @Test
    void sortMovies_throws_error_when_list_parameter_is_empty(){
        //GIVEN
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        ObservableList<Movie> testList = FXCollections.observableArrayList();

        //WHEN
        ObservableList<Movie> functionReturn = homeController.reverseMovies(testList);

        //THEN
        ObservableList<Movie> expectedReturn = FXCollections.observableArrayList();
        String expectedConsoleOutput = "Error: list to sort is empty\n";

        assertEquals(expectedConsoleOutput, outContent.toString());
        Assertions.assertIterableEquals(expectedReturn, functionReturn);
    }

    @Test
    void sortMovies_returns_list_reversed_when_parameter_is_given_ascending() {

    }

    @Test
    void sortMovies_returns_list_reversed_when_parameter_is_given_descending() {

    }

}
    /*List<Movie> expected = Arrays.asList(
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