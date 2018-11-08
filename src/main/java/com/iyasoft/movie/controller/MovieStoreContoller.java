package com.iyasoft.movie.controller;

import java.util.List;
import java.util.function.Predicate;

import com.iyasoft.movie.entity.MovieDetail;
import com.iyasoft.movie.service.MovieStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieStoreContoller {

    @Autowired
    private MovieStoreService movieStoreService;

    private Predicate<String> servicePredicate = service -> !"odm".equalsIgnoreCase(service) && !"dbm".equalsIgnoreCase(service);

    @GetMapping("/api/movies/{title}")
    public ResponseEntity getMoviesByTitle(@PathVariable("title") String title, @RequestParam("api") String api) {//@RequestParam("title") String title, @PathVariable("api") String api
        if (servicePredicate.test(api)) {
            return ResponseEntity.badRequest().build();
        }

        List<MovieDetail> result = movieStoreService.getMoviesDetailFromExternalService(title, api);
        return ResponseEntity.ok(result);
    }

}
