package com.junior.muhammad.popularmovies2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.junior.muhammad.popularmovies2.adapters.MoviesAdapter;
import com.junior.muhammad.popularmovies2.data.MoviesContract;
import com.junior.muhammad.popularmovies2.loaders.FavoriteMoviesLoader;
import com.junior.muhammad.popularmovies2.loaders.MovieAsyncTaskLoader;
import com.junior.muhammad.popularmovies2.models.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MoviesAdapter.OnItemClickListener {

    private static final String TAG = MainActivity.class.toString();
    private Context mContext;

    @BindView(R.id.rv_movies)
    RecyclerView recyclerView;
    @BindView(R.id.pb_searching)
    ProgressBar mProgressBar;

    //string to indicate which sorting mode must be performed it been default on most popular endPoint
    private static String howToSort = Constants.MOST_POPULAR_MOVIES;

    //to save the id about the working loader to not restart the wrong loader in onResume method
    private static int loader_id;

    //list of movies to hold data returned from the loader
    private static ArrayList<Movie> mListOfMovies;

    //list of movies to hold data from favorite database to iterate on it to find favorite movies
    private ArrayList<Movie> mFavoriteMovies;

    private MoviesAdapter adapter;

    int returnValue;

    int tabId;


    /**
     * Loader call back response of instantiating the AsyncTaskLoader to load the movies ArrayList
     * from the internet and then updating the UI
     */
    private final LoaderManager.LoaderCallbacks<ArrayList<Movie>> allMoviesLoader =
            new LoaderManager.LoaderCallbacks<ArrayList<Movie>>() {

                @NonNull
                @Override
                public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
                    return new MovieAsyncTaskLoader(getApplicationContext(), howToSort); // change on user demand

                }

                @Override
                public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

                    //get the loader id
                    loader_id = loader.getId();

                    //method to set the ArrayList of movies to the adapter and set the adapter
                    //on the recycler view
                    extractFetchedMovies(data);
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
                    adapter = new MoviesAdapter(null, null, null);

                }

            };

    private void extractFetchedMovies(ArrayList<Movie> movies) {
        //check if the ArrayList not null and if it's not pass it to the adapter and show the data
        mProgressBar.setVisibility(View.INVISIBLE); // make progressBar invisible after the data is loaded
        adapter = new MoviesAdapter(this, movies, this);
        recyclerView.setAdapter(adapter);
        mListOfMovies = movies; // then pass the list of most popular movies
    }

    /**
     * Loader call back response of instantiating the CursorLoader to load the movies from favorite
     * database and then update the UI
     */
    private final LoaderManager.LoaderCallbacks<Cursor> favoriteLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @NonNull
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    return new FavoriteMoviesLoader(getApplicationContext());
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

                    if (tabId == 3)
                        loader_id = loader.getId();

                    //method to retrieve the data from the database using the returned cursor
                    // and updating the adapter with this data
                    extractFromCursor(data);
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                    adapter = new MoviesAdapter(null, null, null);
                }
            };

    private void extractFromCursor(Cursor data) {

        ArrayList<Movie> movies = new ArrayList<>();

        //if cursor reference is null return no need to continue
        if (data == null) {
            return;
        }

        //get the column index of all the column in the database
        int titleColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_TITLE);
        int ratingColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_RATING);
        int dateColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_RELEASE_DATE);
        int overviewColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_OVERVIEW);
        int posterPathColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_POSTER_PATH);
        int idColumnIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_MOVIE_ID);
        int backDropIndex = data.getColumnIndex(MoviesContract.FavEntry.COLUMN_BACKDROP_IMAGE);

        //while cursor still have rows get data associated with every row
        while (data.moveToNext()) {

            String title = data.getString(titleColumnIndex);
            String rating = data.getString(ratingColumnIndex);
            String date = data.getString(dateColumnIndex);
            String overview = data.getString(overviewColumnIndex);
            String posterPath = data.getString(posterPathColumnIndex);
            String movieId = data.getString(idColumnIndex);
            String backDropImage = data.getString(backDropIndex);

            //put everything in new movie object
            movies.add(new Movie(title, rating, date, overview, posterPath, movieId, backDropImage));
        }

        adapter = new MoviesAdapter(this, movies, this);

        //only update the recycler view if the favorite tab is the selected and this to prevent
        //updating the adapter with favorite movies when init from onCreate
        if (tabId == 3)
            recyclerView.setAdapter(adapter);

        //updating the list that we pass to details activity intent
        mListOfMovies = movies;
        mFavoriteMovies = movies;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (loader_id == Constants.FAVORITES_LOADER) {

            getSupportLoaderManager().restartLoader(Constants.FAVORITES_LOADER, null, favoriteLoader);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.BUNDLE_KEY_FOR_MOVIES, mListOfMovies);

        outState.putParcelable(Constants.BUNDLE_KEY_FOR_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());

        outState.putInt(Constants.TAB_ID_KEY, tabId);

        outState.putInt(Constants.LOADER_ID_KEY, loader_id);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            mListOfMovies = savedInstanceState.getParcelableArrayList(Constants.BUNDLE_KEY_FOR_MOVIES);
            loader_id = savedInstanceState.getInt(Constants.LOADER_ID_KEY);
            tabId = savedInstanceState.getInt(Constants.TAB_ID_KEY);

            mProgressBar.setVisibility(View.INVISIBLE);
            adapter = new MoviesAdapter(this, mListOfMovies, this);
            recyclerView.setAdapter(adapter);

            Parcelable state = savedInstanceState.getParcelable(Constants.BUNDLE_KEY_FOR_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(state);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mContext = getApplicationContext();

        ButterKnife.bind(this);

        //recyclerView setup
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        //init the favoriteLoader to start query the db data to find which movies are favorite
        getSupportLoaderManager().initLoader(Constants.FAVORITES_LOADER, null, favoriteLoader);

        if (tabId != 3) {
            initTheLoaderIfThereConnection();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.popular_menu_button:

                tabId = 1;
                if (isOnline()) {
                    howToSort = Constants.MOST_POPULAR_MOVIES;
                    restartAllMoviesLoader();
                    Toast.makeText(mContext, R.string.most_popular_selected, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.rated_menu_button:
                tabId = 2;
                if (isOnline()) {
                    howToSort = Constants.TOP_RATED_MOVIES;
                    restartAllMoviesLoader();
                    Toast.makeText(mContext, R.string.top_rated_selected, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fav_menu_button:

                tabId = 3;

                mProgressBar.setVisibility(View.INVISIBLE); // make progressBar invisible no need here

                getSupportLoaderManager().restartLoader(Constants.FAVORITES_LOADER, null, favoriteLoader);

                Toast.makeText(mContext, R.string.favorite_selected, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    //Start of methods related to the loader
    private void initTheLoaderIfThereConnection() {
        if (isOnline()) {

            getSupportLoaderManager().initLoader(Constants.MOVIES_LOADER, null, allMoviesLoader);
        } else {
            Toast.makeText(this, R.string.internet_required, Toast.LENGTH_LONG).show();
        }
    }

    private void restartAllMoviesLoader() {
        getSupportLoaderManager().restartLoader(Constants.MOVIES_LOADER, null, allMoviesLoader);
    }


    @Override
    public void onClick(int position) {

        Movie movie = mListOfMovies.get(position);

        //get the movie id from the current movie object
        int selectedId = Integer.parseInt(movie.getMovieId());

        //instantiating an intent to start details activity passing it the parcelable Movie object
        Intent intent = new Intent(this, DetailsScreen.class);
        intent.putExtra(Constants.MOVIE_OBJECT_TAG, movie);
        //passing true if the movie object passed by the intent is favorite and false if it's not
        intent.putExtra(Constants.IS_FAVORITE_TAG, isFavorite(selectedId));
        //using startActivityForResult to have onActivityForResult called when return from details screen
        startActivityForResult(intent, Constants.SECOND_ACTIVITY_REQUEST_CODE);
    }

    /**
     * This is the best logic I was able to come by to find is the movie object is already in
     * favorite database or not
     */
    @NonNull
    private Boolean isFavorite(int selectedId) {

        for (int i = 0; i < mFavoriteMovies.size(); i++) {

            int favoriteIds = Integer.parseInt(mFavoriteMovies.get(i).getMovieId());
            if (selectedId == favoriteIds) {
                return true;
            } else if (selectedId == returnValue) {
                //this if to find that the selected movie is already favorite because when return
                //from the details screen activity the list of mFavoriteMovies doesn't update
                //so if re-entered the same movie it'll appear no favorite even if it does
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //simply this is the returned movie id and assigning the member variable with the new value
        if (resultCode == Activity.RESULT_OK && data != null) {
            returnValue = Integer.parseInt(data.getStringExtra("movie_id"));
        }
    }

    /**
     * A method based on stackOverFlow answer to check if the internet available on the targeted
     * device or not
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

