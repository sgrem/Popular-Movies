package com.example.android.popular_movies.utilities;

import android.util.Log;

import com.example.android.popular_movies.data.Movie;
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
                while (reader.hasNext()) {
                    movieList.add(readMovie(reader));
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
        String original_title = null;
        String poster_path = null;
        String overview = null;
        double vote_average = 0.0;
        String release_date = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    id = reader.nextInt();
                    break;
                case "original_title":
                    original_title = reader.nextString();
                    break;
                case "poster_path":
                    poster_path = reader.nextString();
                    break;
                case "overview":
                    overview = reader.nextString();
                    break;
                case "vote_average":
                    vote_average = reader.nextDouble();
                    break;
                case "release_date":
                    release_date = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Movie(id, original_title, poster_path, overview, vote_average, release_date);
    }
}
