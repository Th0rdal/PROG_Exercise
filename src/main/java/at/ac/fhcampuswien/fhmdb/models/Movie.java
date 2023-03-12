package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;
    private List<Genre> genres;

    public Movie(String title, String description, List<Genre> genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
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

    public static List<Movie> initializeMovies(){
        List<Movie> movies = new ArrayList<>();
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

        movies.add(new Movie("Scooby-Doo", "A speaking dog and a guy on drugs.",
                Arrays.asList(Genre.COMEDY, Genre.ANIMATION)
        ));

        movies.add(new Movie("The Room", "I did not hit her. I did not. Oh, hi Mark.",
                Arrays.asList(Genre.MUSICAL, Genre.ROMANCE)
        ));

        movies.add(new Movie("Attack of the killer Tomatoes.", "True story over the process of making Heinz Ketchup.",
                Arrays.asList(Genre.DOCUMENTARY, Genre.ADVENTURE)
        ));

        movies.add(new Movie("Kung Pow (Chicken): Enter the fist.", "Is it a movie or a dish?",
                Arrays.asList(Genre.BIOGRAPHY, Genre.SPORT)
        ));

        return movies;
    }
}
