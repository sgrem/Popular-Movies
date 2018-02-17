package com.example.android.popular_movies.data;


/**
 * Created by Steve on 11/15/2017.
 */

public class Movie {

    private int id;
    private String originalTitle = null;
    private String posterPath = null;
    private String overview = null;
    private double voteAverage;
    private String releaseDate = null;

    public Movie (){}

    public Movie(int id, String original_title, String poster_path, String overview,
                 double vote_average, String release_date){
        this.id = id;
        this.originalTitle = original_title;
        this.posterPath = poster_path;
        this.overview = overview;
        this.voteAverage = vote_average;
        this.releaseDate = release_date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
