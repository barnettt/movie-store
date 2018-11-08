package com.iyasoft.movie.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.exception.MovieStoreUnparsableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieDirectorService extends AbstractMovieStoreService {

    private final Predicate<String> jobCheck = job -> job.equalsIgnoreCase("director");

    @Value("${movieapi.mdb-dir}")
    private String mdbDirectorServiceURL;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DbmMovieService dbmMovieService;

    public MovieDirectorService() {

    }

    public List<MovieDetail> getMovieDirectorDetails(MovieDetail detail) {
        List<MovieDetail> details = new ArrayList();
        String url = String.format(mdbDirectorServiceURL, detail.getMovieId());
        Map responseObject = (HashMap) movieService.invokeOnService(restTemplate, url, Map.class);
        String director = getDirectorName(responseObject.get("crew"));
        detail.setDirector(director);
        details.add(detail);
        return details;
    }

    public String getDirectorName(final Object responseObject) {

        final List<LinkedHashMap> movieModelMap = (List<LinkedHashMap>) responseObject;
        String director = null;
        try {

            List<LinkedHashMap> jobs = movieModelMap.stream().filter(entry -> entry.keySet().contains("job") && jobCheck.test((String) entry.get("job"))).limit(100).collect(toList());

            if (!jobs.isEmpty()) {
                director = (String) jobs.get(0).get("name");
            }
        } catch (Exception e) {
            throw new MovieStoreUnparsableResponse("Not able to parse DbmMovieService directors info response. ");
        }
        return director;
    }

    public  List<MovieDetail> getMovieDetailFromResponse(final Object model){return Collections.emptyList();}

    public  List<MovieDetail> getMovieDetailFromResponse(final Object model, String requiredMovie){return Collections.emptyList();};
}
