package com.iyasoft.movie.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)

public class MovieStoreControllerCacheTest {

    @Mock
    private MovieStoreService movieStoreService;

    @InjectMocks
    MovieStoreController movieStoreController;

    @Test
    public void canRetrieveMovieTitleFromCache() throws Exception {
        MovieDetail detail = new MovieDetail();
        List<MovieDetail> details = new ArrayList<>();
        detail.setTitle("Incredibles 2");
        details.add(detail);
        when(movieStoreService.retrieveMovieFromCache(anyString(), anyString())).thenReturn(details);
        ResponseEntity result = movieStoreController.getMoviesByTitle("Incredibles 2", "odm");
        List<MovieDetail> entity = (List<MovieDetail>) result.getBody();
        assertThat(entity.get(0).getTitle(), is("Incredibles 2"));
    }

    @Test
    public void cannotRetrieveFromCacheRetrieveFromService() throws Exception {

        MovieDetail detail = new MovieDetail();
        List<MovieDetail> details = new ArrayList<>();
        detail.setTitle("Incredibles 2");
        details.add(detail);
        when(movieStoreService.retrieveMovieFromCache(anyString(), anyString())).thenReturn(new ArrayList());
        when(movieStoreService.getMoviesDetailFromExternalService(anyString(), anyString())).thenReturn(details);

        ResponseEntity result = movieStoreController.getMoviesByTitle("Incredibles 2", "odm");
        List<MovieDetail> entity = (List<MovieDetail>) result.getBody();
        assertThat(entity.get(0).getTitle(), is("Incredibles 2"));
    }

    @Test
    public void cannotRetrieveFromCache() throws Exception {

        when(movieStoreService.retrieveMovieFromCache(anyString(), anyString())).thenReturn(new ArrayList());
        when(movieStoreService.getMoviesDetailFromExternalService(anyString(), anyString())).thenReturn(Collections.EMPTY_LIST);

        ResponseEntity result = movieStoreController.getMoviesByTitle("Incredibles 2", "odm");
        List<MovieDetail> entity = (List<MovieDetail>) result.getBody();
        assertThat(entity, hasSize(0));
    }

}
