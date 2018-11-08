package com.iyasoft.movie.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.client.ExpectedCount.min;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.List;

import com.Application;
import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
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
import org.springframework.test.web.client.UnorderedRequestExpectationManager;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@ActiveProfiles("test")
public class MovieStoreServiceTest2 extends AbstractTestMovieService {

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer server;

    private String dbmURL = "https://api.themoviedb.org/3/search/movie?api_key=40c006b76d21666cdbe6bef858be16ce&language=en-US&query=venom";

    private String odmURL = "http://www.omdbapi.com/?apikey=a93622a2&t=Venom";

    private String castURL = "https://api.themoviedb.org/3/movie/335983/credits?api_key=40c006b76d21666cdbe6bef858be16ce";


    @Autowired
    private MovieStoreService movieStoreService;

    @Before
    public void setUp() {
        server = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build(new NoResetRequestExpectationManager());
        //MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldReceiveInfoFromOMDService() throws Exception {

        server.expect(requestTo(odmURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJsonPayloadFromFile("odm-movie-data.json"), MediaType.APPLICATION_JSON));


        List<MovieDetail> movies = movieStoreService.getMoviesDetailFromExternalService("Venom", "odm");

        server.verify();
        assertThat(movies, notNullValue());
        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getTitle(), is("Venom"));
        assertThat(movies.get(0).getYear(), is(2018));
        assertThat(movies.get(0).getDirector(), is("Ruben Fleischer"));
        assertThat(movies.get(0).getPosterURL(), is("https://m.media-amazon.com/images/M/MV5BNzAwNzUzNjY4MV5BMl5BanBnXkFtZTgwMTQ5MzM0NjM@._V1_SX300.jpg"));

    }


    @Test(expected = MovieStoreUnparsableResponse.class)
    public void shouldTrowExceptionFromOMDService() throws Exception {

        server.expect(min(1), requestTo(odmURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJsonPayloadFromFile("odm-movie-missing-element.json"), MediaType.APPLICATION_JSON));

        List<MovieDetail> movies = movieStoreService.getMoviesDetailFromExternalService("Venom", "odm");

        server.verify();
    }

    @Test
    public void shouldReceiveInfoFromDBMService() throws Exception {

        server.expect(min(1),
                requestTo(dbmURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJsonPayloadFromFile("dbm-movie-data.json"), MediaType.APPLICATION_JSON));

        server.expect(min(1), requestTo(castURL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getJsonPayloadFromFile("dbm-movie-credit-data.json"), MediaType.APPLICATION_JSON));


        List<MovieDetail> movies = movieStoreService.getMoviesDetailFromExternalService("venom", "dbm");

        server.verify();
        assertThat(movies, notNullValue());
        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getTitle(), is("Venom"));
        assertThat(movies.get(0).getYear(), is(2018));
        assertThat(movies.get(0).getDirector(), is("Ruben Fleischer"));
        assertThat(movies.get(0).getPosterURL(), is("/2uNW4WbgBXL25BAbXGLnLqX71Sw.jpg"));

    }

    public class NoResetRequestExpectationManager extends UnorderedRequestExpectationManager {

        @Override
        public void reset() {
            // do not reset or clear the expectation list
        }

    }

}
