package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.GsonBuilder;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MovieAPI {

    OkHttpClient client = new OkHttpClient();
    String baseURL = "https://prog2.fh-campuswien.ac.at";
    Gson gson;
    public MovieAPI() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieTypeAdapter());
        this.gson = gsonBuilder.create();
    }
    public List<Movie> getFullMovieList() throws IOException {
        String url = baseURL + "/movies";
        Request request = new Request.Builder().url(url).removeHeader("User-Agent").addHeader("User-Agent", "http.agent").build();
        try(Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Movie[] movies = this.gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);

        }
    }

    public List<Movie> getFilteredMovieList(String searchText, Genre genre, String releaseYear, String rating) throws IOException {
        boolean firstQuery = true;
        StringBuilder url = new StringBuilder(this.baseURL + "/movies");
        if (!searchText.equals("")) {
            if (firstQuery) {
                url.append("?");
                firstQuery = false;
            }else {
                url.append("&");
            }
            url.append("query=").append(searchText);
        }
        if (genre != Genre.NONE) {
            if (firstQuery) {
                url.append("?");
                firstQuery = false;
            }else {
                url.append("&");
            }
            url.append("genre=").append(genre.toString());
        }
        if (!releaseYear.equals("No release year filter")) {
            if (firstQuery) {
                url.append("?");
                firstQuery = false;
            }else {
                url.append("&");
            }
            url.append("releaseYear=").append(Integer.parseInt(releaseYear));
        }
        if (!rating.equals("No rating filter")) {
            if (firstQuery) {
                url.append("?");
            }else {
                url.append("&");
            }
            url.append("ratingFrom=").append(Double.parseDouble(rating));
        }
        System.out.println(url);
        Request request = new Request.Builder().url(url.toString()).removeHeader("User-Agent").addHeader("User-Agent", "http.agent").build();
        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Movie[] movies = this.gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);
        }
    }
}

/*
 */