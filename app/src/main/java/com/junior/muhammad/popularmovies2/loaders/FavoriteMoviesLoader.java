package com.junior.muhammad.popularmovies2.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.junior.muhammad.popularmovies2.data.MoviesContract;
import com.junior.muhammad.popularmovies2.data.MoviesDbHelper;

/**
 * CursorLoader to load the favorite movies in a background thread from the database and return
 * a cursor
 */
public class FavoriteMoviesLoader extends android.support.v4.content.CursorLoader {


    public FavoriteMoviesLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {

        MoviesDbHelper helper = new MoviesDbHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor;
        try {
            cursor = db.query(
                    MoviesContract.FavEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    MoviesContract.FavEntry._ID);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return cursor;
    }
}


