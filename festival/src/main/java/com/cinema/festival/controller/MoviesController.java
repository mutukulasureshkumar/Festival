package com.cinema.festival.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.festival.model.Movies;
import com.cinema.festival.service.MoviesService;

@RestController
@RequestMapping( value = "movies")
public class MoviesController {

	@Autowired
	private MoviesService moviesService;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Movies> getAllMovies(){
		return moviesService.getAllMoviesFromTodaay();
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public String load() {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
	        File file = new File(classLoader.getResource("ipl.txt").getFile());
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				Movies movie =new Movies();
				String[] splits=sCurrentLine.split("-");
				String[] date=splits[0].split("/");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
				int month;
				if("April".equalsIgnoreCase(date[0]))
					month=3;
				else
					month=4;
				Calendar calendar = new GregorianCalendar(2018,month,Integer.parseInt(date[1]));
				System.out.println(sdf.format(calendar.getTime()));
				movie.setDate(sdf.format(calendar.getTime()));
				String[] cast=splits[1].split(":");
				movie.setHero(getFullName(cast[0]));
				movie.setHeroin(getFullName(cast[1]));
				if("8 p.m.".equalsIgnoreCase(splits[2]))
					movie.setTime("20:00");
				else
					movie.setTime("16:00");
				movie.setTheater(splits[3].toUpperCase());
				moviesService.save(movie);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return "Data Loaded in Database !!";
	}
	
	public String getFullName(String name){
		if("MI".equals(name))
			return "MUMBAI";
		else if("CSK".equals(name))
			return "CHENNAI";
		else if("DD".equals(name))
			return "DELHI";
		else if("KXIP".equals(name))
			return "PUNJAB";
		else if("KKR".equals(name))
			return "KOLKATA";
		else if("RCB".equals(name))
			return "BANGALORE";
		else if("SRH".equals(name))
			return "HYDERABAD";
		else
			return "RAJASTHAN";
	}

}
