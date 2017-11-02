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
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsScreen extends AppCompatActivity {

    private Intent intent;

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

    boolean mIsFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details_screen);

        ButterKnife.bind(this);

        intent = getIntent();

        extractIntentExtrasAndSetTheViews();


        //Todo comment this part
        mFavoriteImageButton.setImageResource(R.drawable.favorite_button_not_selected);

        mFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIsFavorite = true;

                Movie movie = intent.getParcelableExtra(Constants.MOVIE_OBJECT_TAG);

                ContentValues cv = new ContentValues();
                cv.put(MoviesContract.FavEntry.COLUMN_MOVIE_ID, movie.getMovieId());
                cv.put(MoviesContract.FavEntry.COLUMN_TITLE, movie.getOriginalTitle());

                mFavoriteImageButton.setImageResource(R.drawable.favorite_button_selected);

                Uri insert = getContentResolver().insert(MoviesContract.FavEntry.CONTENT_URI, cv);

                if (insert != null) {
                    getContentResolver().notifyChange(insert, null);
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

        mOriginalTitle.setText(movie.getOriginalTitle());
        mUserRating.setText(rating);
        mRatingBar.setRating(fl / 2); //dividing the value of the rate to be the same ratio in 5 star rating bar
        mOverview.setText(movie.getOverView());
        mReleaseDate.setText(dateFormat(date));
        bindImage(movie.getPosterPath());

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

    /**
     * Using the help of picasso library to fetch our images
     */
    private void bindImage(String imgPath) {

        String url = Constants.IMAGE_QUERY_URL + imgPath;

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_loading_image)
                .into(mMoviePoster);

    }
}
