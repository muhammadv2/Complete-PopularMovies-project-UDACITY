package com.example.worldwide.popularmovies1;


class Constants {

    //Intent related CONSTANTS
    final static String MOVIE_OBJECT_TAG = "original title";


    //URL related CONSTANTS
    final static String BASE_QUERY_URL = "https://api.themoviedb.org/3/movie/";
    final static String API_KEY = "d55e616e0c7efb8d7b2bc2edf05cfd94";//Todo add your Api key

    //Image URL related CONSTANTS
    final static String IMAGE_QUERY_URL = "https://image.tmdb.org/t/p/w342";

    //Endpoints CONSTANTS
    final static String MOST_POPULAR_MOVIES = "popular";
    final static String TOP_RATED_MOVIES = "top_rated";

    //loader CONSTANTS
    final static int MOVIES_LOADER = 0;

    //handling Json related CONSTANTS
    final static String RESULT_TAG = "results";
    final static String ORIGINAL_TITLE_TAG = "original_title";
    final static String POSTER_PATH_TAG = "poster_path";
    final static String OVERVIEW_TAG = "overview";
    final static String VOTE_AVERAGE_TAG = "vote_average";
    final static String RELEASE_DATE_TAG = "release_date";

    //Bundle Key
    final static String BUNDLE_KEY_FOR_MOVIES = "movies key";


}
