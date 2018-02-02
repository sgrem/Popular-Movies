package com.example.android.popular_movies.utilities;

import android.net.Uri;

import com.example.android.popular_movies.MainActivity;
import com.example.android.popular_movies.data.Movie;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Steve on 11/8/2017.
 */

public class NetworkUtils {

    private static final Type COLLECTION_TYPE = new TypeToken<List<Movie>>(){}.getType();

    public static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(TmdbConstants.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .registerTypeAdapter(COLLECTION_TYPE, new MovieListTypeAdapter().nullSafe())
                    .create()
            ))
            .build();

    /*public static URL buildTmdbDiscoverUrl(MainActivity.SortMoviesBy sortOrder) {
        String sortOrderQuery = "popularity.desc";
        switch (sortOrder){
            case POPULARITY:
                sortOrderQuery = "popularity.desc";
                break;
            case RATING:
                sortOrderQuery = "vote_average.desc";
                break;
        }

        Uri tmdbUri = Uri.parse(TmdbConstants.TMDB_DISCOVER_URL)
                .buildUpon()
                .appendQueryParameter("sort_by", sortOrderQuery)
                .appendQueryParameter("api_key", TmdbApiKey.TMDB_API )
                .build();
        URL tmdbDiscoverUrl = null;
        try {
            tmdbDiscoverUrl = new URL(tmdbUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return tmdbDiscoverUrl;
    }*/
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    /*public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }*/

    /*public static List<Movie> parseTmdbResults(String apiResponseData){

        JSONObject apiResponseDataJsonObject = null;
        JSONArray resultsJsonArray = null;
        JSONObject movieJsonObject = null;

        try {
            apiResponseDataJsonObject = new JSONObject(apiResponseData);
            resultsJsonArray = apiResponseDataJsonObject.getJSONArray(TmdbConstants.TMDB_RESULTS_KEY);
            Movie movie = null;
            Movie.movieList.clear();
            for(int i = 0; i < resultsJsonArray.length(); i++){
                movieJsonObject = resultsJsonArray.getJSONObject(i);
                movie = new Movie();
                movie.setId(movieJsonObject.getInt(TmdbConstants.TMDB_ID_KEY));
                movie.setOriginal_title(movieJsonObject.getString(TmdbConstants.TMDB_TITLE_KEY));
                movie.setPoster_path(movieJsonObject.getString(TmdbConstants.TMDB_POSTER_KEY));
                movie.setOverview(movieJsonObject.getString(TmdbConstants.TMDB_SYNOPSIS_KEY));
                movie.setVote_average(movieJsonObject.getInt(TmdbConstants.TMDB_USER_RATING_KEY));
                movie.setRelease_date(movieJsonObject.getString(TmdbConstants.TMDB_RELEASE_DATE_KEY));
                Movie.movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Movie.movieList;

    }*/
}
