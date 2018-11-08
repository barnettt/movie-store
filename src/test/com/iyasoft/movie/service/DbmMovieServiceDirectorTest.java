package com.iyasoft.movie.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import com.Application;
import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@ActiveProfiles("test")
public class DbmMovieServiceDirectorTest extends AbstractTestMovieService {


    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer server;

    private String castURL = "https://api.themoviedb.org/3/movie/335983/credits?api_key=40c006b76d21666cdbe6bef858be16ce";

    @Autowired
    private MovieDirectorService movieDirectorService;

    @Before
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getDirectorName() {

        MovieDetail detail = new MovieDetail();
        detail.setMovieId("335983");

        server.reset();

        server.expect(once(), requestTo(castURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJsonPayloadFromFile("dbm-movie-credit-data.json"), MediaType.APPLICATION_JSON));

        List<MovieDetail> movies = movieDirectorService.getMovieDirectorDetails(detail);

        server.verify();
        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getDirector(), is("Ruben Fleischer"));
    }
}
