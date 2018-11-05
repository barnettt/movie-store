package com.iyasoft.movie.service;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.repository.MovieDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MovieStoreService {

    @Autowired
    MovieDetailRepository movieDetailRepository;

    public MovieDetail cacheMovie(final MovieDetail movie) {

        return movieDetailRepository.save(movie);
    }
}
