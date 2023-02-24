package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

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
    void movies_are_sorted_correctly_with_current_sortState_none(){
        //GIVEN
        homeController.initializeState();
        homeController.sortState = SortState.NONE;

        //WHEN
        homeController.sortMovies();

        //THEN
        List<Movie> expected = Arrays.asList(
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

        assertEquals(expected, homeController.observableMovies);

    }

    }