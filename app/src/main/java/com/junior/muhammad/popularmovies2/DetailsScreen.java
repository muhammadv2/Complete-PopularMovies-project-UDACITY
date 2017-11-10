package com.junior.muhammad.popularmovies2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.junior.muhammad.popularmovies2.adapters.ReviewsAdapter;
import com.junior.muhammad.popularmovies2.adapters.TrailersAdapter;
import com.junior.muhammad.popularmovies2.data.MoviesContract;
import com.junior.muhammad.popularmovies2.models.Movie;
import com.junior.muhammad.popularmovies2.models.MovieReviews;
import com.junior.muhammad.popularmovies2.models.MovieTrailer;
import com.junior.muhammad.popularmovies2.utils.ImageUtils;
import com.junior.muhammad.popularmovies2.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsScreen extends AppCompatActivity implements TrailersAdapter.OnItemClickListener {

    @BindView(R.id.tv_original_title)
    TextView mOriginalTitle;
    @BindView(R.id.tv_user_rating)
    TextView mUserRating;
    @BindView(R.id.tv_overview)
    TextView mOverview;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.backDrop_iv)
    ImageView mBackdrop;
    @BindView(R.id.iv_details_activity_poster)
    ImageView mMoviePoster;
    @BindView(R.id.trailers_rv)
    RecyclerView trailerRv;
    @BindView(R.id.reviews_rv)
    RecyclerView reviewsRv;
    @BindView(R.id.tv_reviews_not_available)
    TextView mReviewsNotAvailable;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    TrailersAdapter trailersAdapter;
    ReviewsAdapter reviewsAdapter;

    private Intent intent;

    private Boolean isFavorite;

    private Movie movie;

    private String mMovieId;

    private ArrayList<MovieTrailer> movieTrailers = new ArrayList<>();

    private Toast toast;

    /**
     * Loader call back response of instantiating the AsyncTaskLoader to load the trailers ArrayList
     * from the internet and then updating the UI
     */
    final LoaderManager.LoaderCallbacks<ArrayList<MovieTrailer>> trailersLoader = new
            LoaderManager.LoaderCallbacks<ArrayList<MovieTrailer>>() {
                @NonNull
                @Override
                public Loader<ArrayList<MovieTrailer>> onCreateLoader(int id, Bundle args) {
                    return new TrailersAsyncLoader(getApplicationContext(), mMovieId);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<MovieTrailer>> loader, ArrayList<MovieTrailer> data) {

                    updateTrailersAdapter(data);
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<MovieTrailer>> loader) {

                }
            };

    void updateTrailersAdapter(ArrayList<MovieTrailer> data) {

        trailersAdapter = new TrailersAdapter(this, data,
                this);
        trailerRv.setAdapter(trailersAdapter);

        movieTrailers = data;
    }

    /**
     * Loader call back response of instantiating the AsyncTaskLoader to load the reviews ArrayList
     * from the internet and then updating the UI
     */
    final LoaderManager.LoaderCallbacks<ArrayList<MovieReviews>> reviewsLoader = new
            LoaderManager.LoaderCallbacks<ArrayList<MovieReviews>>() {
                @NonNull
                @Override
                public Loader<ArrayList<MovieReviews>> onCreateLoader(int id, Bundle args) {
                    return new ReviewsAsyncLoader(getApplicationContext(), mMovieId);
                }

                @Override
                public void onLoadFinished(Loader<ArrayList<MovieReviews>> loader, ArrayList<MovieReviews> data) {

                    if (data != null && data.size() > 0) {

                        reviewsRv.setVisibility(View.VISIBLE);
                        reviewsAdapter = new ReviewsAdapter(data);
                        reviewsRv.setAdapter(reviewsAdapter);
                        mReviewsNotAvailable.setVisibility(View.INVISIBLE);

                    } else {
                        mReviewsNotAvailable.setVisibility(View.VISIBLE);
                        reviewsRv.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onLoaderReset(Loader<ArrayList<MovieReviews>> loader) {

                }
            };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(Constants.BUNDLE_KEY_FOR_BOOLEAN, isFavorite);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setUpTrailerRecycler();

        setUpReviewsRecycler();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();
        movie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_TAG);
        mMovieId = movie.getMovieId();

        extractIntentExtrasAndSetTheViews();

        if (savedInstanceState != null) {
            isFavorite = savedInstanceState.getBoolean("boolean_key", false);
        } else {
            isFavorite = intent.getBooleanExtra(Constants.IS_FAVORITE_TAG, false);
        }

        if (isFavorite) {
            floatingActionButton.setImageResource(R.drawable.favorite_button_selected);

        } else {
            floatingActionButton.setImageResource(R.drawable.favorite_button_not_selected);
        }

        //initialization our two loaders for trailers and reviews
        getSupportLoaderManager().initLoader(Constants.TRAILERS_LOADER, null, trailersLoader);
        getSupportLoaderManager().initLoader(Constants.REVIEWS_LOADER, null, reviewsLoader);

        handlingFabClicks();
    }

    private void setUpReviewsRecycler() {
        reviewsRv.setHasFixedSize(true);
        LinearLayoutManager reviewsLm =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewsRv.setLayoutManager(reviewsLm);
    }

    private void setUpTrailerRecycler() {
        trailerRv.setHasFixedSize(true);
        LinearLayoutManager trailersLm =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRv.setLayoutManager(trailersLm);
    }

    private void handlingFabClicks() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFavorite) {
                    //if the movie is favorite set the other image resource when user clicks
                    floatingActionButton.setImageResource(R.drawable.favorite_button_not_selected);

                    //set up the uri to be passed to delete method
                    String stringId = movie.getMovieId();
                    Uri uri = MoviesContract.FavEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    //because the movie is already favorite once the button clicked delete it
                    getContentResolver().delete(uri,
                            null,
                            null);

                    //set the boolean to be false
                    isFavorite = false;

                    String movieName = movie.getTitle() + " ";


                    if (toast != null)
                        toast.cancel();
                    toast = Toast.makeText(getApplicationContext(),
                            movieName + getString(R.string.movie_removed), Toast.LENGTH_LONG);
                    toast.show();
                } else {

                    floatingActionButton.setImageResource(R.drawable.favorite_button_selected);

                    ContentValues cv = new ContentValues();
                    cv.put(MoviesContract.FavEntry.COLUMN_TITLE, movie.getTitle());
                    cv.put(MoviesContract.FavEntry.COLUMN_RATING, movie.getUserRating());
                    cv.put(MoviesContract.FavEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                    cv.put(MoviesContract.FavEntry.COLUMN_OVERVIEW, movie.getOverView());
                    cv.put(MoviesContract.FavEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                    cv.put(MoviesContract.FavEntry.COLUMN_MOVIE_ID, movie.getMovieId());
                    cv.put(MoviesContract.FavEntry.COLUMN_BACKDROP_IMAGE, movie.getBackDrop());

                    //insert method which takes a uri and the content resolver and return a uri
                    Uri insert = getContentResolver().insert(MoviesContract.FavEntry.CONTENT_URI, cv);

                    //if the uri is not null notify the change on it
                    if (insert != null) {
                        getContentResolver().notifyChange(insert, null);
                    }

                    //set the boolean to true
                    isFavorite = true;

                    //set a result to be returned to the MainActivity with the movie id
                    Intent intent = new Intent();
                    intent.putExtra("movie_id", movie.getMovieId());
                    setResult(Activity.RESULT_OK, intent);

                    String movieName = movie.getTitle() + " ";


                    if (toast != null)
                        toast.cancel();
                    toast = Toast.makeText(getApplicationContext(),
                            movieName + getString(R.string.movie_added), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }


    /**
     * help setting the data associated with Intent as extras to our views
     */
    private void extractIntentExtrasAndSetTheViews() {

        Movie movie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_TAG);

        String date = movie.getReleaseDate();

        mOriginalTitle.setText(movie.getTitle());
        mUserRating.setText(movie.getUserRating());
        mOverview.setText(movie.getOverView());
        mReleaseDate.setText(dateFormat(date));
        ImageUtils.bindImage(this, movie.getPosterPath(), mMoviePoster);
        ImageUtils.bindImage(this, movie.getBackDrop(), mBackdrop);
    }

    /**
     * help formatting the date returned for the query to more readable date
     */
    private String dateFormat(String date) {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");

        return timeFormat.format(myDate);
    }

    /**
     * onClick associated with trailers when trailer clicked start an intent from the url of youtube
     * website and the trailer key to allow the user watch the trailers
     */
    @Override
    public void onClick(int position) {

        String trailerKey = movieTrailers.get(position).getTrailerKey();
        String url = Constants.BASE_URL_FOR_TRAILER_VIDEO + trailerKey;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * static class to start the networking call for the trailers endpoint and returning ArrayList
     * of this trailers
     */
    static class TrailersAsyncLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<MovieTrailer>> {

        final String movieId;

        @Override
        protected void onStartLoading() {
            super.onStartLoading();

            forceLoad();
        }

        TrailersAsyncLoader(Context context, String movieId) {
            super(context);

            this.movieId = movieId;
        }

        @Override
        public ArrayList<MovieTrailer> loadInBackground() {
            return NetworkUtils.fetchTrailers(movieId, Constants.TRAILERS_FOR_MOVIE);
        }
    }


    /**
     * static class to start the networking call for the reviews endpoint and returning ArrayList
     * of this reviews
     */
    static class ReviewsAsyncLoader extends AsyncTaskLoader<ArrayList<MovieReviews>> {

        final String movieId;

        @Override
        protected void onStartLoading() {
            super.onStartLoading();

            forceLoad();
        }

        ReviewsAsyncLoader(Context context, String movieId) {
            super(context);

            this.movieId = movieId;
        }

        @Override
        public ArrayList<MovieReviews> loadInBackground() {
            return NetworkUtils.fetchReviews(movieId, Constants.REVIEWS_FOR_MOVIE);
        }
    }
}

