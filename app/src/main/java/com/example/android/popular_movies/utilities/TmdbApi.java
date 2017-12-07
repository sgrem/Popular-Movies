package com.example.android.popular_movies.utilities;

import com.example.android.popular_movies.data.Movie;
import com.squareup.moshi.Moshi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Steve on 11/28/2017.
 */

public interface TmdbApi {

    @GET("/3/discover/movie")
    Call<List<Movie>> getMovies(@Query("sort_by") String sort_by, @Query("api_key") String api_key);


}
