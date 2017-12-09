package com.example.android.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.utilities.TmdbConstants;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvRating;
    private TextView mTvReleaseDate;
    private TextView mTvSynopsis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        mIvImage = findViewById(R.id.iv_detail_image);
        mTvTitle = findViewById(R.id.tv_movie_title);
        mTvRating = findViewById(R.id.tv_movie_rating);
        mTvReleaseDate = findViewById(R.id.tv_movie_release_date);
        mTvSynopsis = findViewById(R.id.tv_movie_synopsis);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        Movie movie = Movie.movieList.get(position);
        if (movie != null){
            Picasso.with(this).load(TmdbConstants.TMDB_POSTER_URL +
                    movie.getPoster_path()).into(mIvImage);
            mTvTitle.setText(movie.getOriginal_title());
            mTvRating.setText(String.valueOf(movie.getVote_average()));
            mTvReleaseDate.setText(movie.getRelease_date());
            mTvSynopsis.setText(movie.getOverview());
        }
    }
}
