package com.junior.muhammad.popularmovies2.data;


import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContractClass {

    /* Content provider constants
     1) Content authority,
     2) Base content URI,
     3) Path(s) to the tasks directory
   */
    static final String AUTHORITY = "com.junior.muhammad.popularmovies2";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Defining the possible paths for accessing data in this contract
    static final String PATH_POPULAR_MOVIES = "popular";

    static final String PATH_TOP_MOVIES = "top";

    static final String PATH_FAV_MOVIES = "favorite";

    //Inner class that defines the contents of popular table
    public static final class PopularEntry implements BaseColumns {

        static final Uri POPULAR_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();

        //Popular table and column names

        static final String TABLE_NAME = "popular";

        static final String COLUMN_TITLE = "title";
        static final String COLUMN_POSTER = "poster";
        static final String COLUMN_OVERVIEW = "overview";
        static final String COLUMN_VOTE = "vote";
        static final String COLUMN_RELEASE_DATE = "release_date";
        static final String COLUMN_TRAILER = "trailer";
        static final String COLUMN_REVIEWS = "review";

    }

    //Inner class that defines the contents of top table
    public static final class TopEntry implements BaseColumns {

        static final Uri TOP_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_MOVIES).build();

        //Top table and column names

        static final String TABLE_NAME = "top";

        static final String COLUMN_TITLE = "title";
        static final String COLUMN_POSTER = "poster";
        static final String COLUMN_OVERVIEW = "overview";
        static final String COLUMN_VOTE = "vote";
        static final String COLUMN_RELEASE_DATE = "release_date";
        static final String COLUMN_TRAILER = "trailer";
        static final String COLUMN_REVIEWS = "review";
    }

    //Inner class that defines the contents of favorite table\
    public static final class FavEntry implements BaseColumns {

        static final Uri FAVORITE_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV_MOVIES).build();

        //Favorite table and column names

        static final String TABLE_NAME = "favorite";

        static final String COLUMN_TITLE = "title";
        static final String COLUMN_POSTER = "poster";
        static final String COLUMN_OVERVIEW = "overview";
        static final String COLUMN_VOTE = "vote";
        static final String COLUMN_RELEASE_DATE = "release_date";
        static final String COLUMN_TRAILER = "trailer";
        static final String COLUMN_REVIEWS = "review";
    }
}
