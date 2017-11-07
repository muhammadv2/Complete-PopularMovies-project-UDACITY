package com.junior.muhammad.popularmovies2;


public class Constants {

    //Intent related CONSTANTS
     final static String MOVIE_OBJECT_TAG = "original title";
     final static String IS_FAVORITE_TAG = "is_it";


    //URL related CONSTANTS
     public final static String BASE_QUERY_URL = "https://api.themoviedb.org/3/movie/";
     public final static String API_KEY = "d55e616e0c7efb8d7b2bc2edf05cfd94";//Todo add your Api key

    //Image URL related CONSTANTS
     public final static String IMAGE_QUERY_URL = "https://image.tmdb.org/t/p/w342";
     final static String BASE_URL_FOR_TRAILER = "https://img.youtube.com/vi/";

    //Endpoints CONSTANTS
     final static String MOST_POPULAR_MOVIES = "popular";
     final static String TOP_RATED_MOVIES = "top_rated";
     public final static String TRAILERS_FOR_MOVIE = "videos";

    //loaders CONSTANTS
     final static int MOVIES_LOADER = 100;
     final static int FAVORITES_LOADER = 200;

    //handling Json related CONSTANTS
     public final static String RESULT_TAG = "results";
     public final static String ORIGINAL_TITLE_TAG = "original_title";
     public final static String POSTER_PATH_TAG = "poster_path";
     public final static String OVERVIEW_TAG = "overview";
     public final static String VOTE_AVERAGE_TAG = "vote_average";
     public final static String RELEASE_DATE_TAG = "release_date";
     public final static String MOVIE_ID = "id";
    //Json for trailer
     public final static String TRAILER_NAME = "name";
     public final static String TRAILER_KEY = "key";

    //Bundle Key
     final static String BUNDLE_KEY_FOR_MOVIES = "movies_key";
     final static String BUNDLE_KEY_FOR_BOOLEAN = "boolean_key";
     final static String BUNDLE_KEY = "movies_bundle";
    
     static final String TRAILER_IMAGE_QLT = "/maxresdefault.jpg";
     static final String BASE_URL_FOR_TRAILER_VIDEO = "https://www.youtube.com/watch?v=";
}
