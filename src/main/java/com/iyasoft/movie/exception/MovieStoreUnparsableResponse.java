package com.iyasoft.movie.exception;

import java.util.NoSuchElementException;

public class MovieStoreUnparsableResponse extends RuntimeException  {
    public MovieStoreUnparsableResponse(final String s) {
        super(s);
    }

    public MovieStoreUnparsableResponse(final String s, final NoSuchElementException e) {
        super(s,e);
    }
}
