package com.cinema.festival.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.festival.model.Movies;


@Repository
public interface MoviesRepository  extends JpaRepository<Movies, Integer>{
	public Movies findByMovieId(int movie_id);
	public List<Movies> findByDateGreaterThanEqual(String date);
	public List<Movies> findByDate(String date);
}
