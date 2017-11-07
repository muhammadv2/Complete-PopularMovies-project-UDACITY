package com.junior.muhammad.popularmovies2.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simply a model class which implementing parcelable interface
 */
public class Movie implements Parcelable {

    private String title, userRating, releaseDate, overView, posterPath, movieId;


    public Movie(String title, String userRating, String releaseDate, String overView,
          String posterPath, String movieId) {

        this.title = title;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overView = overView;
        this.posterPath = posterPath;
        this.movieId = movieId;

    }

    private Movie(Parcel in) {
        title = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        overView = in.readString();
        posterPath = in.readString();
        movieId = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getMovieId() {
        return movieId;
    }


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
        parcel.writeString(overView);
        parcel.writeString(posterPath);
        parcel.writeString(movieId);
    }
}
