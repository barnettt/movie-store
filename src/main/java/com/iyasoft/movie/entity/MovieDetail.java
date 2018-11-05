package com.iyasoft.movie.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class MovieDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String director;
    private String posterURL;
    private Integer year;

    public MovieDetail(final String title, final String director, final String url, final int year) {
        this.title = title;
        this.director = director;
        this.posterURL = url;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(final String posterURL) {
        this.posterURL = posterURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(final Integer year) {
        this.year = year;
    }
}
