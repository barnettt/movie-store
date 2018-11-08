package com.iyasoft.movie.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyasoft.movie.ApplicationTestConfig;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, Application.class})
@ComponentScan(basePackages = {"com.iyaasoft.movie.*"})
@ActiveProfiles("test")
public class OdmMovieServiceTest extends AbstractTestMovieService {

    @Autowired
    OdmMovieService odmMovieService;

    @Test
    public void shouldParseResponseFromOdm() throws Exception {
        JSONObject data  = new JSONObject(getJsonPayloadFromFile("odm-movie-data.json"));
        String modelData = data.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map asList = mapper.readValue(modelData, Map.class);
        List<MovieDetail> details = odmMovieService.getMovieDetailFromResponse(asList);
        assertThat(details, notNullValue());
        assertThat(details, hasSize(1));
    }

    @Test(expected=MovieStoreUnparsableResponse.class)
    public void shouldThrowException() throws Exception {
        JSONObject data  = new JSONObject(getJsonPayloadFromFile("odm-movie-missing-element.json"));
        String modelData = data.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map asMap = mapper.readValue(modelData, Map.class);
        List<MovieDetail> details = odmMovieService.getMovieDetailFromResponse(asMap);
    }

}
