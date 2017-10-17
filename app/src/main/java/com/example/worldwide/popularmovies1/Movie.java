package com.example.worldwide.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

class Movie implements Parcelable {

    private String originalTitle;
    private String userRating;
    private String releaseDate;
    private String overView;
    private String posterPath;


    Movie(String originalTitle, String userRating, String releaseDate, String overView, String posterPath) {

        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.overView = overView;

    }


    private Movie(Parcel in) {
        originalTitle = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        overView = in.readString();
        posterPath = in.readString();
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

    String getOriginalTitle() {
        return originalTitle;
    }

    String getUserRating() {
        return userRating;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    String getOverView() {
        return overView;
    }

    String getPosterPath() {
        return posterPath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(originalTitle);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
        parcel.writeString(overView);
        parcel.writeString(posterPath);
    }
}
