package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WatchlistEntity {
    //TODO Implement
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String apiId;

    public long getId() {
        return id;
    }

    public String getApiId() {
        return apiId;
    }

    public String getDescription() {
        return description;
    }

    public String getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public double getRating() {
        return rating;
    }

    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String genres;
    @DatabaseField
    private int releaseYear;
    @DatabaseField
    private String imgUrl;
    @DatabaseField
    private int lengthInMinutes;
    @DatabaseField
    private double rating;

    public WatchlistEntity() {}
    public WatchlistEntity(String apiId, String title, String description, String genres, int releaseYear, String imgUrl, int lengthInMinutes, double rating) {
        this.apiId = apiId;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.rating = rating;
    }

    public String getTitle() {return this.title;}
    public static String genresToString(List<Genre> genres) {
        StringBuilder temp = new StringBuilder();
        for (Genre tempGenre: genres) {
            temp.append(tempGenre.toString());
            temp.append(",");
        }
        return temp.substring(0, temp.length()-1);
    }

    public static Movie convertWatchlistEntityToMovie(WatchlistEntity watchlistEntity) {
        List<Genre> genres = Arrays.stream(watchlistEntity.getGenres().split(","))
                .map(Genre::valueOf)
                .toList();
        return new Movie(watchlistEntity.getTitle(),
                watchlistEntity.getDescription(),
                genres,
                watchlistEntity.getReleaseYear(),
                watchlistEntity.getApiId(),
                watchlistEntity.getImgUrl(),
                watchlistEntity.getLengthInMinutes(),
                watchlistEntity.getRating());
    }

    public static WatchlistEntity convertMovieToWatchlistEntity(Movie movie) {
        return new WatchlistEntity(movie.getID(),
                movie.getTitle(),
                movie.getDescription(),
                genresToString(movie.getGenres()),
                movie.getReleaseYear(),
                movie.getImgUrl(),
                movie.getLengthInMinutes(),
                movie.getRating());
    }
}
