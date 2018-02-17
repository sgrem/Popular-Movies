package com.example.android.popular_movies.utilities;

import android.util.Log;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.data.MovieList;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 12/7/2017.
 */

public class MovieListTypeAdapter extends TypeAdapter<List<Movie>> {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void write(JsonWriter out, List<Movie> value) throws IOException {

    }

    @Override
    public List<Movie> read(JsonReader reader) throws IOException {
        List<Movie> movieList = new ArrayList<>();
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Log.d(TAG, "Executing: " + name);
            if (name.equals("results")) {
                reader.beginArray();
                movieList.clear();
                while (reader.hasNext()) {
                    Movie movie = readMovie(reader);
                    if (movie != null){
                        movieList.add(movie);
                    }
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        /*} */
        }
        reader.endObject();
        return movieList;
    }

    private Movie readMovie(JsonReader reader) throws IOException {
        int id = 0;
        String originalTitle = null;
        String posterPath = null;
        String overview = null;
        double voteAverage = 0.0;
        String releaseDate = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    id = reader.nextInt();
                    break;
                case "original_title":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    originalTitle = reader.nextString();
                    break;
                case "poster_path":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    posterPath = reader.nextString();
                    break;
                case "overview":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    overview = reader.nextString();
                    break;
                case "vote_average":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    voteAverage = reader.nextDouble();
                    break;
                case "release_date":
                    if (reader.peek() == JsonToken.NULL) {
                        reader.nextNull();
                        continue;
                    }
                    releaseDate = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        if (validateMovieParms(id, originalTitle, posterPath, overview, voteAverage, releaseDate)){
            return new Movie(id, originalTitle, posterPath, overview, voteAverage, releaseDate);
        }
        return null;
    }

    private boolean validateMovieParms(int id, String originalTitle, String posterPath, String overview,
                                       double voteAverage, String releaseDate){
        if (posterPath == null){
            return false;
        }
        return true;
    }
}
