package com.example.android.popular_movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popular_movies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTmdbJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvTmdbJson = (TextView) findViewById(R.id.tv_tmdb_json);
        //Picasso.with(this).load(R.drawable.selfie).into(mIvSelfy);

        new TmdbDiscoverTask().execute(NetworkUtils.buildTmdbDiscoverUrl());
    }

    private class TmdbDiscoverTask extends AsyncTask<URL, Void, List<String>> {

        @Override
        protected List<String> doInBackground(URL... urls) {

            String response = null;
            List<String> movieList = null;
            try {
                response = NetworkUtils.getResponseFromHttpUrl(urls[0]);
                movieList = NetworkUtils.parseTmdbResults(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieList;
        }

        @Override
        protected void onPostExecute(List<String> movieList) {
            super.onPostExecute(movieList);
            if(movieList != null) {
                for (String movie : movieList) {
                    mTvTmdbJson.append(movie + "\n");

                }
            }
        }

    }
}
