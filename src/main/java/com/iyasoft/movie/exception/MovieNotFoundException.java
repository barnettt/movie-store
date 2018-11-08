package com.iyasoft.movie.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(final String message) {
       super(message);
    }
}
