package com.example.android.popular_movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TmdbAdapter.TmdbAdapterOnClickHandler {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerviewTmdb;
    private TmdbAdapter mTmdbAdapter;
    private TextView mTvInternetMessage;
    private Button mBtnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvInternetMessage = findViewById(R.id.tv_internet_message);
        mBtnRetry = findViewById(R.id.btn_retry);
        mRecyclerviewTmdb = findViewById(R.id.recyclerview_tmdb);
        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        /*StaggeredGridLayoutManager layoutManager
                = new StaggeredGridLayoutManager(this, Resources.Theme.obta);*/
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerviewTmdb.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerviewTmdb.setHasFixedSize(true);

        retrievePopularMovies();
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
            new TmdbDiscoverTask().execute(NetworkUtils.buildTmdbDiscoverUrl());
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

    @SuppressLint("StaticFieldLeak")
    private class TmdbDiscoverTask extends AsyncTask<URL, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(URL... urls) {

            String response = null;
            List<Movie> movieList = null;
            try {
                response = NetworkUtils.getResponseFromHttpUrl(urls[0]);
                movieList = NetworkUtils.parseTmdbResults(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);

            mTmdbAdapter = new TmdbAdapter(MainActivity.this, movieList);
            mRecyclerviewTmdb.setAdapter(mTmdbAdapter);
        }

    }

    public void onClickTmdbAdapter(Integer position) {
        Intent detailActivityIntent = new Intent(this, MovieDetailActivity.class);
        detailActivityIntent.putExtra("position", position);
        startActivity(detailActivityIntent);
    }


}
