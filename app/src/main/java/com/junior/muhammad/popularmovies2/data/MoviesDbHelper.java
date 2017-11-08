package com.junior.muhammad.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.junior.muhammad.popularmovies2.data.MoviesContract.*;


public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";

    private static final int VERSION = 3;

    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE "
            + FavEntry.TABLE_NAME + "("
            + FavEntry._ID + " INTEGER PRIMARY KEY,"
            + FavEntry.COLUMN_TITLE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_RATING + " TEXT NOT NULL,"
            + FavEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
            + FavEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL,"
            + FavEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL"
            + ");";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //calling execSQL creates the table from our String
        db.execSQL(CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
