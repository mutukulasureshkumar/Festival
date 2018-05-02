package com.cinema.festival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.festival.model.Ratio;


@Repository
public interface RatioRepository  extends JpaRepository<Ratio, Integer>{
	public Ratio findByMovieId(int movie_id);
	public void deleteByMovieId(int movie_id);
}
