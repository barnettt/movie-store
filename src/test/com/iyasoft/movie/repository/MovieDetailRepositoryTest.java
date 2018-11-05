package com.iyasoft.movie.repository;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.Application;
import com.iyasoft.movie.entity.MovieDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
public class MovieDetailRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    MovieDetailRepository movieDetailRepository;

    @Test
    public void canSaveMovieDetail() {
        MovieDetail movie = new MovieDetail("Incredibles", "John Doe", "http://someurl/image", 2004);
        movie = movieDetailRepository.save(movie);
        MovieDetail response = entityManager.find(MovieDetail.class, movie.getId());
        assertThat(response.getId(), is(movie.getId()));

    }


}
