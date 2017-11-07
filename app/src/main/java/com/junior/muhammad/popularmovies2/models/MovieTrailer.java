package com.junior.muhammad.popularmovies2.models;

public class MovieTrailer {

    private final String  key;
    private final String name;

    public MovieTrailer( String key, String name) {


        this.key = key;
        this.name = name;

    }

    public String getTrailerKey() {
        return key;
    }

    public String getTrailerName() {
        return name;
    }
}
