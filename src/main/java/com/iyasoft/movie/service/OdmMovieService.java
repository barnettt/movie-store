package com.iyasoft.movie.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OdmMovieService extends AbstractMovieStoreService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${movieapi.odm}")
    private String odmServiceURL;

    public OdmMovieService() {

    }

    private BiPredicate<Object, Object> checkFields = (a, b) -> a == null || b == null;

    @Override
    public List<MovieDetail> getMovieDetailFromResponse(final Object model, final String requiredMovie) {
        return Collections.emptyList();
    }

    @Override
    public List<MovieDetail> getMovieDetailFromResponse(final Object model) {

        Map odmModel = (HashMap) model;
        if (checkFields.test(odmModel.get("Title"), odmModel.get("Director"))) {
            throw new MovieStoreUnparsableResponse("Mandatory fields missing form response");
        }

        List<MovieDetail> detail = new ArrayList<>();
        detail.add(new MovieDetail((String) odmModel.get("Title"), (String) odmModel.get("Director"), (String) odmModel.get("Poster"), Integer.valueOf(((String) odmModel.get("Year")))));
        return detail;
    }

    public List<MovieDetail> invokeUrlForOdmService(String title) {
        String url = odmServiceURL + title;
        Map<String, Object> response = (HashMap) invokeServiceFunction(restTemplate, url, Map.class);
        return getMovieDetailFromResponse(response);
    }

}
