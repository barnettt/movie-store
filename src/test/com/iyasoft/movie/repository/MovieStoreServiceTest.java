package com.iyasoft.movie.repository;

import com.Application;
import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieNotFoundException;
import com.iyasoft.movie.repository.MovieDetailRepository;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@ActiveProfiles("test")
public class MovieStoreServiceTest {

    @Mock
    MovieDetailRepository movieDetailRepository;

    @InjectMocks
    MovieStoreService movieStore;

    @Test
    public void shouldStoreMovieDetailInDbCache() {

        MovieDetail movie = new MovieDetail("Incredibles", "John Doe", "http://someurl/image",2004);
        movie.setId(1234l);
        when(movieDetailRepository.save(any(MovieDetail.class))).thenReturn(movie);

        MovieDetail response  = movieStore.cacheMovie(movie);
        assertThat(response, notNullValue());
        assertThat(response.getId(), notNullValue());

    }

    @Test
    public void shouldFindMovieInDbCacheByTitle() {
        List<MovieDetail> movies = new ArrayList<>();

        MovieDetail movie1 = new MovieDetail("Incredibles", "John Doe", "http://someurl/image",2004);
        movie1.setId(1234l);
        MovieDetail movie2 = new MovieDetail("Incredibles 2", "John Doe", "http://someurl/image",2018);
        movie2.setId(1234l);
        movies.add(movie1);
        movies.add(movie2);

        when(movieDetailRepository.findByTitle(anyString())).thenReturn(movies);
        List<MovieDetail> result = movieStore.getMoviesByTitle("Incredibles 2");

    }

    @Test(expected = MovieNotFoundException.class)
    public void shouldThrowMovieNotFoundException () {
        when(movieDetailRepository.findByTitle(anyString())).thenReturn(new ArrayList());
        List<MovieDetail> result =  movieStore.getMoviesByTitle("Don't Bother me");
    }

    @Test(expected = MovieNotFoundException.class)
    public void shouldThrowMovieNotFoundException_forNullResult () {
        when(movieDetailRepository.findByTitle(anyString())).thenReturn(null);
        List<MovieDetail> result =  movieStore.getMoviesByTitle("Don't Bother me");
    }



}
