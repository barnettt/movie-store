package com.iyasoft.movie.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;


import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;

import com.Application;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
import com.iyasoft.movie.model.DbmMovieModel;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@ActiveProfiles("test")
public class DbmMovieServiceTest extends AbstractTestMovieService {

    @Autowired
    DbmMovieService dbmMovieService;

    @Test
    public void shouldParseResponseFromODM() throws Exception {
        JSONObject data  = new JSONObject(getJsonPayloadFromFile("dbm-movie-data.json"));
        String modelData = data.getJSONArray("results").toString();
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap> asList = mapper.readValue(modelData, List.class);
//
        List<MovieDetail> details = dbmMovieService.getMovieDetailFromResponse(asList, "Venom");
        assertThat(details, notNullValue());
        assertThat(details, hasSize(1));
    }

    @Test(expected=MovieStoreUnparsableResponse.class)
    public void shouldThrowException() throws Exception {
        JSONObject data  = new JSONObject(getJsonPayloadFromFile("dbm-movie-missing-element.json"));
        String modelData = data.getJSONArray("results").toString();
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap> asList = mapper.readValue(modelData, List.class);
        List<MovieDetail> details = dbmMovieService.getMovieDetailFromResponse(asList, "Venom");
    }

}
