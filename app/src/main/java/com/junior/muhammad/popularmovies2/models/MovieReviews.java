package com.junior.muhammad.popularmovies2.models;


public class MovieReviews {

    private final String reviewAuthor;
    private final String reviewContent;

    public MovieReviews(String author, String content) {
        reviewAuthor = author;
        reviewContent = content;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }
}
