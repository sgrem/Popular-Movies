package com.example.android.popular_movies.data;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 11/15/2017.
 */

public class MovieList {

    public static List<Movie> movieList = new ArrayList<>();

    // total no. of pages to load. Initial load is page 1, after which 2 more pages will load.
    public static final int TOTAL_PAGES = 3;
    // indicates the current page which Pagination is fetching.
    private static int pagesLoaded = 0;

    public static int getPagesLoaded() {
        return pagesLoaded;
    }

    public static void setPagesLoaded(int pagesLoaded) {
        MovieList.pagesLoaded = pagesLoaded;
    }

}
