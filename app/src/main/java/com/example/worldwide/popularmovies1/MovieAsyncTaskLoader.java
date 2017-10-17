package com.example.worldwide.popularmovies1;

import android.content.Context;

import java.util.ArrayList;


class MovieAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Movie>> {

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

    MovieAsyncTaskLoader(Context context, String howToSort) {
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
