package com.example.android.popular_movies.utilities;

import android.net.Uri;

import com.example.android.popular_movies.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Steve on 11/8/2017.
 */

public class NetworkUtils {

    public static URL buildTmdbDiscoverUrl() {

        Uri tmdbUri = Uri.parse(TmdbConstants.TMDB_DISCOVER_URL)
                .buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", TmdbApi.TMDB_API )
                .build();
        URL tmdbDiscoverUrl = null;
        try {
            tmdbDiscoverUrl = new URL(tmdbUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return tmdbDiscoverUrl;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
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
    }

    public static List<Movie> parseTmdbResults(String apiResponseData){

        JSONObject apiResponseDataJsonObject = null;
        JSONArray resultsJsonArray = null;
        JSONObject movieJsonObject = null;

        try {
            apiResponseDataJsonObject = new JSONObject(apiResponseData);
            resultsJsonArray = apiResponseDataJsonObject.getJSONArray(TmdbConstants.TMDB_RESULTS_KEY);
            Movie movie = null;
            Movie.MOVIE_LIST.clear();
            for(int i = 0; i < resultsJsonArray.length(); i++){
                movieJsonObject = resultsJsonArray.getJSONObject(i);
                movie = new Movie();
                movie.setId(movieJsonObject.getInt(TmdbConstants.TMDB_ID_KEY));
                movie.setTitle(movieJsonObject.getString(TmdbConstants.TMDB_TITLE_KEY));
                movie.setPosterImageThumbnail(movieJsonObject.getString(TmdbConstants.TMDB_POSTER_KEY));
                movie.setPlotSynopsis(movieJsonObject.getString(TmdbConstants.TMDB_SYNOPSIS_KEY));
                movie.setUserRating(movieJsonObject.getInt(TmdbConstants.TMDB_USER_RATING_KEY));
                movie.setReleaseDate(movieJsonObject.getString(TmdbConstants.TMDB_RELEASE_DATE_KEY));
                Movie.MOVIE_LIST.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Movie.MOVIE_LIST;

    }
}
