package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    public String title;
    public String description;
    public List<Genre> genres;
    public int releaseYear;
    public String id;
    public String imgURL;
    public int lengthInMinutes;
    public List<String> directors;
    public List<String> writers;
    public List<String> mainCast;
    public double rating;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }
    public Movie(String title, String description, List<Genre> genres, int releaseYear, String id, String imgURL, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.id = id;
        this.imgURL = imgURL;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;

    }


    @Override  // Methode to compare to movies.
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        Movie movie = (Movie) o;
        return this.title.equals(movie.title) && this.description.equals(movie.description) && this.genres.equals(movie.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres (){return genres;}

    public static List<Movie> initializeMovies() throws IOException {
        MovieAPI movieAPI = new MovieAPI();
        return movieAPI.getFullMovieList();
    }
}
