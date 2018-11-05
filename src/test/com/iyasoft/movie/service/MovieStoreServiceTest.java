package com.iyasoft.movie.service;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.repository.MovieDetailRepository;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
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






}
