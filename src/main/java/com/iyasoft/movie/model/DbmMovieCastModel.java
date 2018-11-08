package com.iyasoft.movie.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DbmMovieCastModel implements MovieModel {
    String creditId;
    String department;
    String  gender;
    Integer id;
    String job;
    String name;
    String profilePath;

}
