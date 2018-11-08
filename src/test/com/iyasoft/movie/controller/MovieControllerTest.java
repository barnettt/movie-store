package com.iyasoft.movie.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.Application;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(MovieStoreContoller.class)
public class MovieControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MovieStoreService movieStoreService;

    @Test
    public void shouldGetMovieFromOMDApi() throws Exception {

        MvcResult result = doMvcTestAndGetResult("odm");

        assertThat(result.getResponse().getContentAsString(), is(not("[]")));
    }


    @Test
    public void shouldGetMovieFromDBMApi() throws Exception {

        MvcResult result = doMvcTestAndGetResult("dbm");

        assertThat(result.getResponse().getContentAsString(), is(not("[]")));
    }

    private MvcResult doMvcTestAndGetResult(String api) throws Exception {
        MovieDetail detail =  new MovieDetail();
        List<MovieDetail> details = new ArrayList<>();
        details.add(detail);
        when(movieStoreService.getMoviesDetailFromExternalService(anyString(),anyString())).thenReturn(details);

        return mvc.perform(get("/api/movies/Incredibles?api="+api)
                .header("Origin", "localhost")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk()).andReturn();
    }
}