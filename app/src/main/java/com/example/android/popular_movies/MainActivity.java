package com.example.android.popular_movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.data.MovieList;
import com.example.android.popular_movies.utilities.NetworkUtils;
import com.example.android.popular_movies.utilities.TmdbApi;
import com.example.android.popular_movies.utilities.TmdbApiKey;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements TmdbAdapter.TmdbAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerviewTmdb;
    private TmdbAdapter mTmdbAdapter;
    private TextView mTvInternetMessage;
    private Button mBtnRetry;

    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;



    public enum SortMoviesBy {POPULARITY, RATING}
    private SortMoviesBy movieSortOrder = SortMoviesBy.POPULARITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvInternetMessage = findViewById(R.id.tv_internet_message);
        mBtnRetry = findViewById(R.id.btn_retry);
        mRecyclerviewTmdb = findViewById(R.id.recyclerview_tmdb);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerviewTmdb.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerviewTmdb.setHasFixedSize(true);
        mRecyclerviewTmdb.setItemAnimator(new DefaultItemAnimator());

        mTmdbAdapter = new TmdbAdapter(MainActivity.this, MovieList.movieList);
        mRecyclerviewTmdb.setAdapter(mTmdbAdapter);

        mRecyclerviewTmdb.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                retrievePopularMovies();
            }

            @Override
            public boolean isLastPage() {
                return MovieList.getPagesLoaded() > MovieList.TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        if (MovieList.getPagesLoaded() == 0) {
            retrievePopularMovies();
        }
    }

    private void retrievePopularMovies(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Log.i(LOG_TAG, "Running TmdbDiscoverTask");
            connectedLayout();
            new TmdbDiscoverTask().execute(movieSortOrder);
        } else {
            Log.i(LOG_TAG, "Skipping TmdbDiscoverTask: Not Connected to Internet");
            notConnectedLayout();
        }
    }

    private void connectedLayout(){
        mTvInternetMessage.setVisibility(View.GONE);
        mBtnRetry.setVisibility(View.GONE);
    }

    private void notConnectedLayout(){
        mTvInternetMessage.setVisibility(View.VISIBLE);
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    public void onClickRetry(View v){
        retrievePopularMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_movies, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MovieList.movieList.clear();
        MovieList.setPagesLoaded(0);

        switch (id) {
            case R.id.action_sort_by_popularity:
                movieSortOrder = SortMoviesBy.POPULARITY;
                new TmdbDiscoverTask().execute(movieSortOrder);
                return true;
            case R.id.action_sort_by_rating:
                movieSortOrder = SortMoviesBy.RATING;
                new TmdbDiscoverTask().execute(movieSortOrder);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class TmdbDiscoverTask extends AsyncTask<MainActivity.SortMoviesBy, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoading = true;
        }

        @Override
        protected List<Movie> doInBackground(MainActivity.SortMoviesBy... sortOrder) {
            String sortOrderQuery = "popularity.desc";
            switch (sortOrder[0]){
                case POPULARITY:
                    sortOrderQuery = "popularity.desc";
                    break;
                case RATING:
                    sortOrderQuery = "vote_average.desc";
                    break;
            }

            List<Movie> movieList = null;
            TmdbApi tmdbApi = NetworkUtils.RETROFIT.create(TmdbApi.class);
            Call<List<Movie>> call = tmdbApi.getMovies(sortOrderQuery, MovieList.getPagesLoaded() + 1, TmdbApiKey.TMDB_API);
            try {
                movieList = call.execute().body();
            } catch (IOException e) {
                movieList = null;
                e.printStackTrace();
            }

            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            if (movieList != null) {
                MovieList.movieList.addAll(movieList);
                MovieList.setPagesLoaded(MovieList.getPagesLoaded() + 1); //Increment page index to load the next one

                mTmdbAdapter.notifyDataSetChanged();
            }
            isLoading = false;
        }

    }

    public void onClickTmdbAdapter(Integer position) {
        Intent detailActivityIntent = new Intent(this, MovieDetailActivity.class);
        detailActivityIntent.putExtra("position", position);
        startActivity(detailActivityIntent);
    }


}
