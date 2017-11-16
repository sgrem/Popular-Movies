package com.example.android.popular_movies;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TmdbAdapter.TmdbAdapterOnClickHandler {

    private RecyclerView mRecyclerviewTmdb;
    private TmdbAdapter mTmdbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        new TmdbDiscoverTask().execute(NetworkUtils.buildTmdbDiscoverUrl());
    }

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
            /*if (movieList != null) {
                for (String movie : movieList) {
                    mTvTmdbJson.append(movie + "\n");

                }
            }*/
        }

    }

    public void onClickTmdbAdapter(String movie) {
        //TODO Open movie detail activity
    }
}
