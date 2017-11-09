package com.example.android.popular_movies.utilities;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Steve on 11/8/2017.
 */

public class NetworkUtils {

    public static final String TMDB_DISCOVER = "https://api.themoviedb.org/3/discover/movie";
    public static final String TMDB_POSTER = "https://image.tmdb.org/t/p/w500";

    public static URL buildTmdbDiscoverUrl() {

        Uri tmdbUri = Uri.parse(TMDB_DISCOVER)
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

    public static List<String> parseTmdbResults(String results){

        List<String> resultsList = new ArrayList<>();
        JSONObject resultsJsonObject = null;
        JSONArray resultsJsonArray = null;
        JSONObject movieJsonObject = null;

        try {
            resultsJsonObject = new JSONObject(results);
            resultsJsonArray = resultsJsonObject.getJSONArray("results");
            for(int i = 0; i < resultsJsonArray.length(); i++){
                movieJsonObject = resultsJsonArray.getJSONObject(i);
                resultsList.add(movieJsonObject.getString("title"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultsList;

    }
}
