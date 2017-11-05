package com.junior.muhammad.popularmovies2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.junior.muhammad.popularmovies2.data.MoviesContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsScreen extends AppCompatActivity {

    @BindView(R.id.tv_original_title)
    TextView mOriginalTitle;
    @BindView(R.id.tv_user_rating)
    TextView mUserRating;
    @BindView(R.id.details_favorite_button)
    ImageButton mFavoriteImageButton;
    @BindView(R.id.tv_overview)
    TextView mOverview;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.iv_details_activity_poster)
    ImageView mMoviePoster;

    private Intent intent;
    private Boolean isFavorite;
    private Movie movie;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(Constants.BUNDLE_KEY_FOR_BOOLEAN, isFavorite);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details_screen);

        ButterKnife.bind(this);

        //instantiating the intent from the coming intent
        intent = getIntent();
        //getting the movie object from the intent
        movie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_TAG);
        //set all the views up
        extractIntentExtrasAndSetTheViews();
        //handles the button response to favorite and un favorite movies
        favoriteMoviesButtonHandling();

        //check if the saved instance is not null pass the saved boolean key to keep the boolean
        //value when orientation changes
        if (savedInstanceState != null) {
            isFavorite = savedInstanceState.getBoolean("boolean_key", false);
        } else {
            isFavorite = intent.getBooleanExtra(Constants.IS_FAVORITE_TAG, false);
        }

        //upon the boolean set image resource to recognize that the movie is favorite or not
        if (isFavorite) {
            mFavoriteImageButton.setImageResource(R.drawable.favorite_button_selected);

        } else {
            mFavoriteImageButton.setImageResource(R.drawable.favorite_button_not_selected);
        }


    }

    /**
     * method response to insert or delete the current movie from database upon user clicks
     */
    private void favoriteMoviesButtonHandling() {

        mFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if statement to find if the movie is already in favorite or not
                if (isFavorite) {
                    //if the movie is favorite set the other image resource when user clicks
                    mFavoriteImageButton.setImageResource(R.drawable.favorite_button_not_selected);

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
                } else {

                    //if the movie is not favorite then set the image to tell that its now favorite
                    mFavoriteImageButton.setImageResource(R.drawable.favorite_button_selected);

                    //creating a content values putting all the data within every cloumn
                    ContentValues cv = new ContentValues();
                    cv.put(MoviesContract.FavEntry.COLUMN_TITLE, movie.getTitle());
                    cv.put(MoviesContract.FavEntry.COLUMN_RATING, movie.getUserRating());
                    cv.put(MoviesContract.FavEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                    cv.put(MoviesContract.FavEntry.COLUMN_OVERVIEW, movie.getOverView());
                    cv.put(MoviesContract.FavEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
                    cv.put(MoviesContract.FavEntry.COLUMN_MOVIE_ID, movie.getMovieId());

                    //insert method which takes a uri and the content resolver and return a uri
                    Uri insert = getContentResolver().insert(MoviesContract.FavEntry.CONTENT_URI, cv);

                    //if the uri is not null notify the change on it
                    if (insert != null) {
                        getContentResolver().notifyChange(insert, null);
                    }

                    //set the boolean to true
                    isFavorite = true;


                    //TODO save the object is the favorite button pressed on it to add the favorite list in the mainactivity
                    //to not appear un favorite if re entered without restarting the loader

                }
            }
        });

    }

    /**
     * help setting the data associated with Intent as extras to our views
     */
    private void extractIntentExtrasAndSetTheViews() {

        Movie movie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_TAG);

        String rating = movie.getUserRating();
        float fl = Float.valueOf(rating); //turning the string into float to be used in rating bar

        String date = movie.getReleaseDate();

        mOriginalTitle.setText(movie.getTitle());
        mUserRating.setText(rating);
        mRatingBar.setRating(fl / 2); //dividing the value of the rate to be the same ratio in 5 star rating bar
        mOverview.setText(movie.getOverView());
        mReleaseDate.setText(dateFormat(date));
        ImageUtils.bindImage(this, movie.getPosterPath(), mMoviePoster);

        mRatingBar.setIsIndicator(true); // set the rating bar as indicator to prevent editing on it

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


}
