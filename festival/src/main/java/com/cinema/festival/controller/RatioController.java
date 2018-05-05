package com.cinema.festival.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.festival.model.Ratio;
import com.cinema.festival.service.RatioService;

@RestController
@RequestMapping( value = "ratios")
public class RatioController {

	@Autowired
	private RatioService ratioService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Ratio> getAllRatios(){
		return ratioService.getAllRatios();
	}
	
	@RequestMapping(value = "/bymovie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Ratio getRatioByMovie(@RequestParam int movieId){
		return ratioService.getRatioByMovieId(movieId);
	}
	
	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public Ratio getRatioByMovie(@RequestBody Ratio ratio){
		return ratioService.save(ratio);
	}
}
