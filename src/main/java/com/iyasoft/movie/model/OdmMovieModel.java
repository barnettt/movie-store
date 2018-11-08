package com.iyasoft.movie.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OdmMovieModel implements MovieModel {

    @JsonProperty(value="Title", required=true)
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    @JsonProperty(value="Title", required=true)
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private List<Rating> ratings;
    private String metascore;
    private String imdbRating;
    private String imdbVotes;
    private String imdbID;
    private String type;
    private String dVD;
    private String boxOffice;
    private String production;
    private String website;
    private String response;
}
