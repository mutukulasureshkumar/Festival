package com.cinema.festival.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.cinema.festival.model.Patrons;
import com.cinema.festival.service.PatronsService;

@RestController
@RequestMapping( value = "patrons")
public class PatronsController {
	
	@Autowired
	private PatronsService patronService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<Patrons> getAll(){
		return (ArrayList<Patrons>) patronService.getAll();
	}
		
	@RequestMapping(method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public void save(@RequestBody  Patrons patron){
		patronService.save(patron);
	}
	
	@RequestMapping(value = "/byuser", method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public Patrons get(@RequestParam String username, @RequestParam String password){
		return patronService.getPatron(username, password);
	}
	
	@RequestMapping(value = "/bypatronid", method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public Patrons get(@RequestParam String patronid){
		return patronService.getPatron(Integer.parseInt(patronid));
	}
	
	@RequestMapping(method = RequestMethod.DELETE,  consumes = MediaType.APPLICATION_JSON_VALUE)
	public void delete(@RequestBody  Patrons patron){
		patronService.deletePatron(patron);
	}
	
	@RequestMapping(value = "/checkbalnce", method = RequestMethod.GET)
	public double checkBalance(){
		return patronService.checkBalance();
	}

	@RequestMapping(value = "/updatebalance/{username}/{balance}",  method = RequestMethod.GET)
	public String updateBalace(@PathVariable("username") String username, @PathVariable("balance") double balance){
		if(username == null || username == " "){
			return "Invalid Username!!";
		}
		Patrons patrons = patronService.updateBalance(username.trim(), balance);
		if(patrons != null)
			return "Updated balance of user : "+patrons.getFull_name()+" is "+patrons.getBalance();
		else
			return "Transaction got Failed!!!";
	}
}
