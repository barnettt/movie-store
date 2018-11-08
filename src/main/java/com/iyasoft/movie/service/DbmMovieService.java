package com.iyasoft.movie.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
import com.iyasoft.movie.exception.URIEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DbmMovieService extends AbstractMovieStoreService {


    @Value("${movieapi.mdb}")
    private String mdbServiceURL;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public DbmMovieService() {
    }

    @Override
    public List<MovieDetail> getMovieDetailFromResponse(final Object model, String requestedMovie) {

        List<LinkedHashMap> movieModelMap = (List<LinkedHashMap>) model;

        List<MovieDetail> detail = new ArrayList<>();

        try {
            movieModelMap.stream().forEach(item -> {
                Optional<String> title = item.keySet().stream().filter(d -> d.equals("title")).findFirst();
                if (((String) item.get(title.get())).equalsIgnoreCase(requestedMovie) && detail.isEmpty()) {
                    MovieDetail movie = new MovieDetail((String) item.get(title.get()), null, (String) item.get("poster_path"), Integer.valueOf(((String) item.get("release_date")).substring(0, 4)));
                    String id = item.get("id") != null ? item.get("id").toString() : null;
                    movie.setMovieId(id);
                    detail.add(movie);
                }
            });
        } catch (NoSuchElementException e) {
            throw new MovieStoreUnparsableResponse("Cannot parse the response data, element missing", e);
        }
        return detail;
    }

    @Override
    public List<MovieDetail> getMovieDetailFromResponse(final Object model) {

        return Collections.emptyList();
    }

    public List<MovieDetail> getInvokeUrlForMovieDetails(String title) {
        String url = mdbServiceURL + doEncoding(title);
        HashMap response = (HashMap) invokeServiceFunction(restTemplate, url, Map.class);
        List<MovieDetail> detail = getMovieDetailFromResponse(response.get("results"), title);
        return detail;
    }

    private String doEncoding(final String title) {
        try {
            return URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            throw new URIEncodingException(e);
        }
    }
}
