package com.junior.muhammad.popularmovies2.loaders;

import android.content.Context;

import com.junior.muhammad.popularmovies2.Movie;
import com.junior.muhammad.popularmovies2.NetworkUtils;

import java.util.ArrayList;


public class MovieAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Movie>> {

    private String mHowToSort;

    private ArrayList<Movie> mResult;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mResult != null) {
            deliverResult(mResult);
        } else {
            forceLoad(); // Force an asynchronous load

        }
    }

    public MovieAsyncTaskLoader(Context context, String howToSort) {
        super(context);

        mHowToSort = howToSort; //used to determine on what the query url will perform
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        mResult = data;
        super.deliverResult(data);
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        //start fetching the data from the internet in a background thread
        return NetworkUtils.fetchData(mHowToSort);
    }
}
