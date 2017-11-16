package com.example.android.popular_movies.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 11/15/2017.
 */

public class Movie {

    public static final List<Movie> MOVIE_LIST = new ArrayList<>();

    private int id;
    private String title = null;
    private String posterImageThumbnail = null;
    private String plotSynopsis = null;
    private float userRating;
    private String releaseDate = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImageThumbnail() {
        return posterImageThumbnail;
    }

    public void setPosterImageThumbnail(String posterImageThumbnail) {
        this.posterImageThumbnail = posterImageThumbnail;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
