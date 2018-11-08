package com.iyasoft.movie.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {

    private long id;
    private String title;
    private String director;
    private String posterURL;
    private Integer year;


}
