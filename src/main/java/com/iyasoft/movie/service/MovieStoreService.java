package com.iyasoft.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieNotFoundException;
import com.iyasoft.movie.repository.MovieDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieStoreService {

    @Autowired
    MovieDetailRepository movieDetailRepository;

    @Autowired
    OdmMovieService odmMovieService;

    @Autowired
    DbmMovieService dbmMovieService;

    @Autowired
    MovieDirectorService movieDirectorService;

    public MovieStoreService() {
        // default constructor
    }

    private Predicate<List> moviePredicate = movie -> movie == null || movie.isEmpty();

    public List<MovieDetail> cacheMovie(final List<MovieDetail> movies) {
        List<MovieDetail> results = new ArrayList<>();
        movies.forEach(movie -> results.add(movieDetailRepository.save(movie)));
        return results;
    }


    public List<MovieDetail> getMoviesByTitle(final String title) {

        List<MovieDetail> movies = movieDetailRepository.findByTitle(title);
        if (moviePredicate.test(movies)) {
            throw new MovieNotFoundException(String.format("No movie with title : %s found", title));
        }
        return movies;
    }

    public List<MovieDetail> getMoviesDetailFromExternalService(final String title, final String serv) {
        List<MovieDetail> details;
        if ("odm".equalsIgnoreCase(serv)) {
            details = odmMovieService.invokeUrlForOdmService(title);

        } else {
            details = dbmMovieService.getInvokeUrlForMovieDetails(title);
            details = getMovieDirectorFromService(details.get(0));
        }
        return details;
    }

    private List<MovieDetail> getMovieDirectorFromService(MovieDetail detail) {
        return movieDirectorService.getMovieDirectorDetails(detail);
    }

    public List<MovieDetail> retrieveMovieFromCache(final String title, String api) {
        return movieDetailRepository.findByTitleAndApi(title, api);
    }
}
