package com.iyasoft.movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
public class MovieStoreTest {



    MovieStore movieStore = new MovieStore();

    @Test
    public void shouldStoreMovieDetailInDbCache() {

        Movie movie = new Movie();

        assertThat(movieStore.cacheMovie(movie), is(true));

    }



}
