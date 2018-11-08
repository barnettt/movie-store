package com.iyasoft.movie.service;

import java.util.List;

import com.iyasoft.movie.entity.MovieDetail;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractMovieStoreService {

    public abstract List<MovieDetail> getMovieDetailFromResponse(final Object model, String requiredMovie);

    public abstract List<MovieDetail> getMovieDetailFromResponse(final Object model);

    public Object invokeServiceFunction(RestTemplate template, String url, Class clazz) {

        return template.getForObject(url, clazz);
    }

}
