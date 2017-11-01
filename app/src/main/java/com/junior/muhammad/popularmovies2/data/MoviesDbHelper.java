package com.junior.muhammad.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.junior.muhammad.popularmovies2.data.MoviesContractClass.*;


public class MoviesDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "moviesDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    /*
    CREATE TABLE `popular` (
	`id`	INTEGER,
	`title`	TEXT NOT NULL,
	`image`	INTEGER NOT NULL,
	`overview`	TEXT NOT NULL,
	`vote`	TEXT NOT NULL,
	`release_date`	TEXT NOT NULL,
	`trailers`	TEXT NOT NULL,
	`reviews`	TEXT NOT NULL,
	PRIMARY KEY(`id`)
);
     */

    private static final String CREATE_TABLE_POPULAR = "CREATE TABLE "
            + PopularEntry.TABLE_NAME + "("
            + PopularEntry._ID + " INTEGER PRIMARY KEY,"
            + PopularEntry.COLUMN_TITLE + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_POSTER + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_TRAILER + " TEXT NOT NULL,"
            + PopularEntry.COLUMN_REVIEWS + " TEXT NOT NULL,"
            + ");";


    private static final String CREATE_TABLE_TOP = "CREATE TABLE "
            + TopEntry.TABLE_NAME + "("
            + TopEntry._ID + " INTEGER PRIMARY KEY,"
            + TopEntry.COLUMN_TITLE + " TEXT NOT NULL,"
            + TopEntry.COLUMN_POSTER + " TEXT NOT NULL,"
            + TopEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
            + TopEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + TopEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + TopEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
            + TopEntry.COLUMN_TRAILER + " TEXT NOT NULL,"
            + TopEntry.COLUMN_REVIEWS + " TEXT NOT NULL,"
            + ");";


    private static final String CREATE_TABLE_FAVORITE = "CREATE TABLE "
            + FavEntry.TABLE_NAME + "("
            + FavEntry._ID + " INTEGER PRIMARY KEY,"
            + FavEntry.COLUMN_TITLE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_POSTER + " TEXT NOT NULL,"
            + FavEntry.COLUMN_OVERVIEW + " TEXT NOT NULL,"
            + FavEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_VOTE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL,"
            + FavEntry.COLUMN_TRAILER + " TEXT NOT NULL,"
            + FavEntry.COLUMN_REVIEWS + " TEXT NOT NULL,"
            + ");";


    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_POPULAR);
        db.execSQL(CREATE_TABLE_TOP);
        db.execSQL(CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PopularEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TopEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavEntry.TABLE_NAME);

        onCreate(db);
    }
}
