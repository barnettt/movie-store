package com.iyasoft.movie.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DbmMovieModel implements MovieModel {
    @JsonProperty(value="vote_count")
    private Integer voteCcount;
    private String id;
    private String video;
    @JsonProperty(value="vote_average")
    private Double voteAverage;
    @JsonProperty(value="title", required=true)
    private String title;
    private Double popularity;
    @JsonProperty(value="poster_path")
    private String poster;
    @JsonProperty(value="original_language")
    private String originalLanguage;
    @JsonProperty(value="original_title")
    private String originalTitle;
    @JsonProperty(value="genre_ids")
    private List<Integer> genreIds; // List
    @JsonProperty(value="backdrop_path")
    private String backdropPath;
    private String adult;
    private String overview;
    @JsonProperty(value="release_date", required=true)
    private String releaseDate;
}

