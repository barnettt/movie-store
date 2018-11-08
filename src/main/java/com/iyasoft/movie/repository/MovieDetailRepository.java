package com.iyasoft.movie.repository;

import java.util.List;

import com.iyasoft.movie.entity.MovieDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailRepository extends CrudRepository<MovieDetail, Long> {

    List<MovieDetail> findByTitle(String title);

    @Query(value = "SELECT * FROM Movie_Detail m WHERE m.title like ?1%",
            nativeQuery = true)
    List<MovieDetail> findByMatchCriteriaStartsWith(String title);

    List<MovieDetail> findByDirector(String director);
}
