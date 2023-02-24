package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    // TODO add more properties here
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        if (object == this){
            return true;
        }
        if(!(object instanceof Movie other)){
            return false;
        }
        return this.title.equals(other.title) && this.description.equals(other.description) && this.genres.equals(other.genres);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres (){return genres;}

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
        // TODO add some dummy data here
        movies.add(new Movie(
                "Bamboo House",
                "Movie about the struggle of the chinese resistance in wwII.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)
        ));

        movies.add(new Movie(
                "Death Race 2000",
                "Race through the United States in the far distant future of the year 2000",
                Arrays.asList(Genre.ADVENTURE, Genre.ACTION)
        ));

        movies.add(new Movie(
                "Generic Movie",
                "The most generic movie ever made.",
                Arrays.asList(Genre.COMEDY, Genre.CRIME)
        ));

        return movies;
    }
}
