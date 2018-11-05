package com.iyasoft.movie.repository;

import com.iyasoft.movie.entity.MovieDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailRepository  extends CrudRepository<MovieDetail, Long>{

}
