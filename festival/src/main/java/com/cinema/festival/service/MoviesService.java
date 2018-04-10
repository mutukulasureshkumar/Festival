package com.cinema.festival.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.festival.model.Movies;
import com.cinema.festival.repository.MoviesRepository;
import com.cinema.festival.util.Util;


@Service
public class MoviesService {
	
	@Autowired
	private MoviesRepository moviesRepository;
	
	public Movies save(Movies movies){
		return moviesRepository.save(movies);
	}
	
	public Movies getMovieByMovieId(int movie_id){
		return moviesRepository.findByMovieId(movie_id);
	}

	public ArrayList<Movies> getAllMovies(){
		return (ArrayList<Movies>) moviesRepository.findAll();
	}
	
	public ArrayList<Movies> getAllMoviesFromTodaay(){
		String date = Util.getDate("dd/M/yyyy");
		return (ArrayList<Movies>) moviesRepository.findByDateGreaterThanEqual(date);
	}
	
	public ArrayList<Movies> getByDate(String date){
		return (ArrayList<Movies>) moviesRepository.findByDate(date);
	}
}
