package com.iyasoft.movie.repository;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import java.util.List;

import com.Application;
import com.iyasoft.movie.entity.MovieDetail;
import org.junit.Before;
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

    MovieDetail movie1;

    @Before
    public void setup() {
        movie1 = new MovieDetail("Incredible Game", "John Doe", "http://someurl/image", 2004);
        MovieDetail movie2 = new MovieDetail("Impossible Journey", "John Doe", "http://someurl/image", 2004);
        MovieDetail movie3 = new MovieDetail("Incredibles 2", "James Brown", "http://someurl/image", 2004);
        entityManager.persist(movie1);
        entityManager.persist(movie2);
        entityManager.persist(movie3);
    }

    @Test
    public void canSaveMovieDetail() {
        MovieDetail response = entityManager.find(MovieDetail.class, movie1.getId());
        assertThat(response.getId(), is(movie1.getId()));

    }

    @Test
    public void canFindByTitle() {

        List<MovieDetail> result = movieDetailRepository.findByTitle("Incredibles 2");
        assertThat(result, hasSize(1));
        assertThat(result.get(0).getTitle(), is("Incredibles 2"));
    }

    @Test
    public void canFindWithMatchCriteria( ) {

        List<MovieDetail> result = movieDetailRepository.findByMatchCriteriaStartsWith("Incredible");
        assertThat(result, hasSize(2));
        result.forEach(movie -> assertThat(movie.getTitle(), containsString("Incredible"))) ;
    }

    @Test
    public void canFindByDirector() {

        List<MovieDetail> result = movieDetailRepository.findByDirector("James Brown");
        assertThat(result,hasSize(1));
        assertThat(result.get(0).getDirector(),is("James Brown"));
    }
}
