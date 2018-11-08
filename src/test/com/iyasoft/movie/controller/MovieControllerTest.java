package com.iyasoft.movie.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.service.MovieStoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(MovieStoreController.class)
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
        MovieDetail detail = new MovieDetail();
        List<MovieDetail> details = new ArrayList<>();
        details.add(detail);
        when(movieStoreService.getMoviesDetailFromExternalService(anyString(), anyString())).thenReturn(details);

        return doMvcCall("incredible game", api);
    }

    private MvcResult doMvcCall(String title, String api) throws Exception {
        return mvc.perform(get(String.format("/api/movies/%s?api=%s", title, api))
                .header("Origin", "localhost")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk()).andReturn();
    }
}