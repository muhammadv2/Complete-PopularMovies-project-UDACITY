package com.junior.muhammad.popularmovies2.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {

    /* Content provider constants
     1) Content authority,
     2) Base content URI,
     3) Path(s) to the tasks directory
   */
    static final String AUTHORITY = "com.junior.muhammad.popularmovies2";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    static final String PATH_FAV_MOVIES = "favorite";

    //Inner class that defines the contents of favorite table\
    public static final class FavEntry implements BaseColumns {

        //content uri that will be used to connect with this table
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIES).build();

        //Favorite table and column names
        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_MOVIE_ID = "movieId";

    }
}
