package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class DescendingState implements SortState {
    @Override
    public void sort(ObservableList<Movie> moviesList) {

        moviesList.sort(Comparator.comparing(Movie::getTitle).reversed());

    }

}
