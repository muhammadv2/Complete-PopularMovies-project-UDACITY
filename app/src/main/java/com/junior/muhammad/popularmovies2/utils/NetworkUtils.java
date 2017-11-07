package com.junior.muhammad.popularmovies2.utils;


import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.junior.muhammad.popularmovies2.Constants;
import com.junior.muhammad.popularmovies2.models.Movie;
import com.junior.muhammad.popularmovies2.models.MovieReviews;
import com.junior.muhammad.popularmovies2.models.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class NetworkUtils {

    private static OkHttpClient okHttp;
    private static Request request;
    private static Response response;

    private NetworkUtils() {
        // creating a private constructor because no one should instantiate this class
    }

    /**
     * fetchData method take the url as a String @param howToSort and do the http connection on it
     * after creating it as a URL and then read the respond and pass it to Movies ArrayList after
     * handling the Json and then
     *
     * @return ArrayList<Movie>
     */
    public static ArrayList<Movie> fetchData(String howToSort) {

        URL url = null;
        try {
            url = getApiUrl(howToSort);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            okHttp = new OkHttpClient();
            request = new Request.Builder().url(url).build();
        }
        String responseBody = null;
        try {
            response = okHttp.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) { //to check if the response returned successfully or not
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handleJson(responseBody);
    }

    public static ArrayList<MovieTrailer> fetchTrailers(String movieId) {

        URL url = null;

        try {
            url = getExtraUrl(movieId);
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            okHttp = new OkHttpClient();
            request = new Request.Builder().url(url).build();
        }

        String responseBody = null;
        try {
            response = okHttp.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) { //to check if the response returned successfully or not
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handleTrailerJson(responseBody);

    }

    public static ArrayList<MovieReviews> fetchReviews(String movieId) {

        URL url = null;

        try {
            url = getExtraUrl(movieId);
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }

        if (url != null) {
            okHttp = new OkHttpClient();
            request = new Request.Builder().url(url).build();
        }

        String responseBody = null;
        try {
            response = okHttp.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) { //to check if the response returned successfully or not
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return handleReviewJson(responseBody);

    }

    private static URL getExtraUrl(String movieId) throws MalformedURLException {

        Uri uri = Uri.parse(Constants.BASE_QUERY_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(Constants.TRAILERS_FOR_MOVIE)
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build();

        return new URL(uri.toString());
    }

    /**
     * build the URL and ducking the exception to be caught when called
     */
    private static URL getApiUrl(String howToSort) throws MalformedURLException {

        Uri builtUri = Uri.parse(Constants.BASE_QUERY_URL).buildUpon()
                .appendPath(howToSort)
                .appendQueryParameter("api_key", Constants.API_KEY)
                .build();

        return new URL(builtUri.toString());
    }

    /**
     * The method responsableR of handling the json returned from the request and extracting all the
     * data needed and creating an Movie array with it and then return it
     */
    private static ArrayList<Movie> handleJson(String response) {

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(response);
            JSONArray resultArray = baseJson.getJSONArray(Constants.RESULT_TAG);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject singleMovieObject = resultArray.getJSONObject(i);

                String originalTitle = singleMovieObject.optString(Constants.ORIGINAL_TITLE_TAG);
                String userRating = singleMovieObject.optString(Constants.VOTE_AVERAGE_TAG);
                String releaseDate = singleMovieObject.optString(Constants.RELEASE_DATE_TAG);
                String overview = singleMovieObject.optString(Constants.OVERVIEW_TAG);
                String posterPath = singleMovieObject.optString(Constants.POSTER_PATH_TAG);
                String movieId = singleMovieObject.optString(Constants.MOVIE_ID);

                movies.add(new Movie(originalTitle, userRating, releaseDate, overview, posterPath, movieId));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private static ArrayList<MovieTrailer> handleTrailerJson(String response) {

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        ArrayList<MovieTrailer> movieTrailers = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(response);
            JSONArray resultArray = baseJson.getJSONArray(Constants.RESULT_TAG);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject singeTrailer = resultArray.getJSONObject(i);

                String trailerKey = singeTrailer.optString(Constants.TRAILER_KEY);
                String trailerName = singeTrailer.optString(Constants.TRAILER_NAME);

                movieTrailers.add(new MovieTrailer(trailerKey, trailerName));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieTrailers;
    }


    private static ArrayList<MovieReviews> handleReviewJson(String responseBody) {

        if (TextUtils.isEmpty(responseBody)) {
            return null;
        }

        ArrayList<MovieReviews> movieReviews = new ArrayList<>();

        try {
            JSONObject baseJson = new JSONObject(responseBody);
            JSONArray resultArray = baseJson.getJSONArray(Constants.RESULT_TAG);

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject singeTrailer = resultArray.getJSONObject(i);

                String author = singeTrailer.optString(Constants.REVIEW_AUTHOR);
                String content = singeTrailer.optString(Constants.REVIEW_CONTENT);

                movieReviews.add(new MovieReviews(author, content));
                Log.d("network", "handleTrailerJson() called with: response = [" + author + "]");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieReviews;
    }

}
