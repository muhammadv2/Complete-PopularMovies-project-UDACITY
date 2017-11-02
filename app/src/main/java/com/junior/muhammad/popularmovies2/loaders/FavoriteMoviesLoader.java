package com.junior.muhammad.popularmovies2.loaders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.junior.muhammad.popularmovies2.data.MoviesContract;
import com.junior.muhammad.popularmovies2.data.MoviesDbHelper;


public class FavoriteMoviesLoader extends android.support.v4.content.CursorLoader {

    Context context;

    public FavoriteMoviesLoader(Context context) {
        super(context);

        this.context = context;
    }

    @Override
    public Cursor loadInBackground() {

        Uri uri = MoviesContract.FavEntry.CONTENT_URI;

        MoviesDbHelper helper = new MoviesDbHelper(context);
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


        } catch (Exception e){
            e.printStackTrace();

            return null;

        }

        if (cursor != null) {
            context.getContentResolver().notifyChange(uri, null);
            return cursor;
        } else {
            throw new UnsupportedOperationException("Undefined URI" + uri);
        }

    }

}
