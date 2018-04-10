package com.cinema.festival.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.festival.model.Ratio;
import com.cinema.festival.repository.RatioRepository;

@Service
public class RatioService {
	
	@Autowired
	private RatioRepository ratioRepository;
	
	public Ratio save(Ratio ratio){
		return ratioRepository.save(ratio);
	}
	
	public Ratio getRatioByMovieId(int movie_id){
		return ratioRepository.findByMovieId(movie_id);
	}
	
	public void deleteAll(){
		ratioRepository.deleteAll();
	}
	
	public ArrayList<Ratio> getAllRatios(){
		return (ArrayList<Ratio>) ratioRepository.findAll();
	}

}
