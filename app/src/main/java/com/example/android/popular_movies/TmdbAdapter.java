package com.example.android.popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popular_movies.data.Movie;
import com.example.android.popular_movies.utilities.TmdbConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Steve on 11/8/2017.
 */

public class TmdbAdapter extends RecyclerView.Adapter<TmdbAdapter.TmdbAdapterViewHolder> {

    private TmdbAdapterOnClickHandler mClickHandler;
    private List<Movie> mMovieList;

    public interface TmdbAdapterOnClickHandler{
        void onClickTmdbAdapter(Integer position);
    }

    public TmdbAdapter(TmdbAdapterOnClickHandler tmdbAdapterOnClickHandler, List<Movie> movieList) {
        mClickHandler = tmdbAdapterOnClickHandler;
        mMovieList = movieList;
    }


    @Override
    public TmdbAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.tmdb_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new TmdbAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TmdbAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class TmdbAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mTmdbImageView;

        public TmdbAdapterViewHolder(View view) {
            super(view);
            mTmdbImageView = view.findViewById(R.id.iv_popular_movie);
            Context context = mTmdbImageView.getContext();
            Picasso.with(context).load(R.drawable.ic_launcher_background).into(mTmdbImageView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Integer position = (Integer) mTmdbImageView.getTag();
            mClickHandler.onClickTmdbAdapter(position);
        }

        public void bind(int position){
            Context context = mTmdbImageView.getContext();
            mTmdbImageView.setOnClickListener(this);
            mTmdbImageView.setTag(Integer.valueOf(position));
            Picasso.with(context).load(TmdbConstants.TMDB_POSTER_URL +
                    mMovieList.get(position).getPosterPath()).into(mTmdbImageView);
        }

    }
}
