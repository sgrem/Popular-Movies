package com.example.android.popular_movies.utilities;

import com.example.android.popular_movies.data.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Steve on 11/28/2017.
 */

public interface TmdbApi {

    @GET("/3/movie/popular")
    Call<List<Movie>> getPopularMovies(
            @Query("page") int pageIndex,
            @Query("api_key") String api_key);

    @GET("/3/movie/top_rated")
    Call<List<Movie>> getTopRatedMovies(
            @Query("page") int pageIndex,
            @Query("api_key") String api_key);


}
