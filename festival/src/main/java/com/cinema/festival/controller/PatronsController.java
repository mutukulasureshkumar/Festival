package com.cinema.festival.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.festival.model.Patrons;
import com.cinema.festival.service.PatronsService;

@RestController
@RequestMapping( value = "patrons", consumes = MediaType.APPLICATION_JSON_VALUE)
public class PatronsController {
	
	@Autowired
	private PatronsService patronService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<Patrons> getAll(){
		return (ArrayList<Patrons>) patronService.getAll();
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody  Patrons patron){
		patronService.save(patron);
	}
	
	@RequestMapping(value = "/byuser", method = RequestMethod.GET)
	public Patrons get(@RequestParam String username, @RequestParam String password){
		return patronService.getPatron(username, password);
	}
	
	@RequestMapping(value = "/bypatronid", method = RequestMethod.GET)
	public Patrons get(@RequestParam String patronid){
		return patronService.getPatron(Integer.parseInt(patronid));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody  Patrons patron){
		patronService.deletePatron(patron);
	}
}