package com.cinema.festival.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.festival.model.Ratio;
import com.cinema.festival.repository.RatioRepository;

@Service
@Transactional 
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
	
	public void deleteAll(int id){
		ratioRepository.deleteByMovieId(id);
	}
	
	public ArrayList<Ratio> getAllRatios(){
		return (ArrayList<Ratio>) ratioRepository.findAll();
	}
}
