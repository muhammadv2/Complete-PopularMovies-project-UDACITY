package com.junior.muhammad.popularmovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class MoviesContentProvider extends ContentProvider {

    // Define final integer constants for the directory of tables and a single items.

    public static final int FAVORITE = 300;
    public static final int FAVORITE_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     * Initialize a new matcher object without any matches,
     * then use .addURI(String authority, String path, int match) to add matches
     */
    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAV_MOVIES, FAVORITE);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAV_MOVIES + "/#", FAVORITE_WITH_ID);

        return uriMatcher;

    }

    // Member variable for MoviesDbHelper that's initialized in the onCreate() method

    private MoviesDbHelper mMoviesHelper;

    @Override
    public boolean onCreate() {

        //initialize a DbHelper to gain access to it.
        mMoviesHelper = new MoviesDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mMoviesHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor cursor;

        switch (match) {

            case FAVORITE:

                cursor = db.query(MoviesContract.FavEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        MoviesContract.FavEntry._ID);

                break;
            default:
                throw new UnsupportedOperationException("Undefined URI" + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mMoviesHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);


        switch (match) {

            case FAVORITE:

                long id = db.insert(MoviesContract.FavEntry.TABLE_NAME,
                        null,
                        values);

                if (id > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);

                    return ContentUris.withAppendedId(MoviesContract.FavEntry.CONTENT_URI, id);
                } else {
                    throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
                }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = mMoviesHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {

            case FAVORITE_WITH_ID:

                String id = uri.getPathSegments().get(1);

                selection = "_id=?";
                selectionArgs = new String[]{id};

                int rows = db.delete(MoviesContract.FavEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                if (rows != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                    return rows;
                } else {
                    throw new UnsupportedOperationException("Unable to delete this : " + uri);

                }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }


    /*
    No need to use the last 2 methods
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
