package com.cinema.festival.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.festival.model.Balance;
import com.cinema.festival.model.Results;
import com.cinema.festival.model.Transactions;
import com.cinema.festival.service.TransactionsService;

@RestController
@RequestMapping( value = "transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionsController {
	
	@Autowired
	private TransactionsService transactionsService;

	
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<Transactions> getAllTransactions(){
		return transactionsService.getAllTransactions();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public HashMap<Integer, ArrayList<Results>> save(@RequestBody  Transactions transactions){
		System.out.println("***LOG INFO*** :: "+transactions.toString());
		return transactionsService.save(transactions, false);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public HashMap<String, ArrayList<Results>> update(@RequestBody Transactions transactions){
		System.out.println("***LOG INFO*** :: "+transactions.toString());
		return transactionsService.update(transactions);
	}
	
	@RequestMapping(value = "/ofuser" ,method = RequestMethod.GET)
	public ArrayList<Transactions> getTransactionsOfUser(@RequestParam String username, @RequestParam String password){
		return transactionsService.getTransactionsByPatronId(username, password);
	}
	
	@RequestMapping(value = "/bymovie" ,method = RequestMethod.GET)
	public ArrayList<Transactions> getTransactionsOfUser(@RequestParam String movieid){
		return transactionsService.getTransactionsByMovieId(Integer.parseInt(movieid));
	}
	
	@RequestMapping(value = "/byuser" ,method = RequestMethod.GET)
	public HashMap<String, ArrayList<Transactions>> getTransactionsByUser(){
		return transactionsService.getTransactionsByUsers();
	}
	
	@RequestMapping(value = "/results" ,method = RequestMethod.GET)
	public HashMap<String, ArrayList<Results>> getTransactionsByUser1(){
		return transactionsService.getAllTransactionsByUsers();
	}
	
	@RequestMapping(value = "/current" ,method = RequestMethod.GET)
	public HashMap<Integer, ArrayList<Results>> getCurrentTransactions(){
		return transactionsService.getCurrentDateMovies();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(){
		transactionsService.deleteAll();
	}
	
	@RequestMapping(value = "/id/{id}",  method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id){
		transactionsService.deleteAll(id);
	}
	
	@RequestMapping(value = "/temp" ,method = RequestMethod.PUT)
	public void getCurrentTransactions1(@RequestBody Transactions results){
		transactionsService.up1(results);
	}
	
	@RequestMapping(value = "/alert" ,method = RequestMethod.GET)
	public void alert(){
		//Schedular schedular = new Schedular();
		//schedular.sendAlertM9();
	}
	
	@RequestMapping(value = "/checkbalance/{movieid}", method = RequestMethod.GET)
	public HashMap<String, ArrayList<Balance>> checkBalance(@PathVariable("movieid") int movieid){
		return transactionsService.getBalnaceByUsers(movieid);
	}
	
	@RequestMapping(value = "/balancebymatch", method = RequestMethod.GET)
	public HashMap<String, Double> checkBalanceByMatch(){
		return transactionsService.getBalnaceByMatch();
	}
}
