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
}
