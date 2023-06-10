package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class MovieSorter {

    private SortState state;

    public MovieSorter(){
        state = new UnsortedState();

    }

    public void setState(SortState state) {this.state = state;}


    public void sortMovies(ObservableList<Movie> movieList) {state.sort(movieList);}
}
