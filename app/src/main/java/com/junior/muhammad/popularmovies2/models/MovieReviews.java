package com.junior.muhammad.popularmovies2.models;


public class MovieReviews {

    String reviewAuthor, reviewContent;

    public MovieReviews(String author, String content){
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
