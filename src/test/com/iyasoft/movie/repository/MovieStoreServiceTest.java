package com.iyasoft.movie.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.Application;
import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieNotFoundException;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MovieStoreServiceTest {

    @Mock
    MovieDetailRepository movieDetailRepository;

    @InjectMocks
    MovieStoreService movieStore;

    @Test
    public void shouldStoreMovieDetailInDbCache() {

        MovieDetail movie = new MovieDetail("Incredibles", "John Doe", "http://someurl/image", 2004);
        movie.setId(1234l);
        when(movieDetailRepository.save(any(MovieDetail.class))).thenReturn(movie);
        List<MovieDetail> movies = new ArrayList<>();
        movies.add(movie);
        List<MovieDetail> response = movieStore.cacheMovie(movies);
        assertThat(response, notNullValue());
        assertThat(response.get(0).getId(), notNullValue());

    }

    @Test
    public void shouldRetrieveMovieFromCache() {
        MovieDetail movie = new MovieDetail("Incredibles", "John Doe", "http://someurl/image", 2004);
        movie.setId(1234l);
        List<MovieDetail> movies = new ArrayList<>();
        movies.add(movie);
        when(movieDetailRepository.findByTitle(anyString())).thenReturn(movies);

        List<MovieDetail> response = movieStore.retrieveMovieFromCache("Incredibles", "odm");
        assertThat(response, notNullValue());
        assertThat(response.get(0).getTitle(), is("Incredibles"));
    }

    @Test
    public void shouldFindMovieInDbCacheByTitle() {
        List<MovieDetail> movies = new ArrayList<>();

        MovieDetail movie1 = new MovieDetail("Incredibles", "John Doe", "http://someurl/image", 2004);
        movie1.setId(1234l);
        MovieDetail movie2 = new MovieDetail("Incredibles 2", "John Doe", "http://someurl/image", 2018);
        movie2.setId(1234l);
        movies.add(movie1);
        movies.add(movie2);

        when(movieDetailRepository.findByTitle(anyString())).thenReturn(movies);
        List<MovieDetail> result = movieStore.getMoviesByTitle("Incredibles 2");

    }

    @Test(expected = MovieNotFoundException.class)
    public void shouldThrowMovieNotFoundException() {
        when(movieDetailRepository.findByTitle(anyString())).thenReturn(new ArrayList());
        List<MovieDetail> result = movieStore.getMoviesByTitle("Don't Bother me");
    }

    @Test(expected = MovieNotFoundException.class)
    public void shouldThrowMovieNotFoundException_forNullResult() {
        when(movieDetailRepository.findByTitle(anyString())).thenReturn(null);
        List<MovieDetail> result = movieStore.getMoviesByTitle("Don't Bother me");
    }


}
