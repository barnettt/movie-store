package com.iyasoft.movie.service;

import java.util.List;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.function.InvokeServiceFunction;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractMovieStoreService {

    public abstract List<MovieDetail> getMovieDetailFromResponse(final Object model, String requiredMovie);

    public abstract List<MovieDetail> getMovieDetailFromResponse(final Object model);

    public InvokeServiceFunction<RestTemplate, String, Class, Object> movieService =  (template, url, clazz) ->   template.getForObject(url, clazz);


}
