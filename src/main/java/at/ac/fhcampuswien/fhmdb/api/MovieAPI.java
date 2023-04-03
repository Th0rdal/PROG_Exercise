package at.ac.fhcampuswien.fhmdb.api;

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
    public List<Movie> getFullMovieList() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieTypeAdapter());
        Gson gson = gsonBuilder.create();
        String url = baseURL + "/movies";
        Request request = new Request.Builder().url(url).removeHeader("User-Agent").addHeader("User-Agent", "http.agent").build();
        try(Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Movie[] movies = gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);

        }
    }
}
