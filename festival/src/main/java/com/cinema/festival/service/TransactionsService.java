package com.cinema.festival.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.cinema.festival.model.Movies;
import com.cinema.festival.model.Patrons;
import com.cinema.festival.model.Ratio;
import com.cinema.festival.model.Results;
import com.cinema.festival.model.Transactions;
import com.cinema.festival.repository.TransactionsRepository;
import com.cinema.festival.util.Util;

@Service
public class TransactionsService {

	@Autowired
	private TransactionsRepository transactionsRepository;
	@Autowired
	private PatronsService patronsService;
	@Autowired
	private RatioService ratioService;
	@Autowired
	private MoviesService moviesService;

	public HashMap<Integer, ArrayList<Results>> save(Transactions transactions) {
		Patrons patron = null;
		Ratio ratio = null;
		Movies movie = null;
		Transactions prevTransaction = null;
		
		/***Validating patron*/
		patron = patronsService.getPatron(transactions.getUsername(), transactions.getPassword());
		if (patron == null)
			throw new BadCredentialsException("username and password are not matched!!");
				
		/**Retrieving the movie details*/
		movie = moviesService.getMovieByMovieId(transactions.getMovieId());
		
		/***Validating current date guess time*/
		String date = Util.getDate("dd/M/yyyy");
		System.out.println("****************************************************************************************** date :: "+ date);
		if (date.compareTo(movie.getDate()) == 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			int current_time = Integer.parseInt(sdf.format(new Date().getTime()));
			System.out.println("***************************************************************************************** time :: "+ current_time);
			if(current_time >= 14)
				throw new IllegalAccessError("Guess is not allowed after 2 PM !!");
		}
		
		/**Setting data in transaction table*/
		date = Util.getDate("dd/M/yyyy  hh:mm:ss");
		transactions.setDate_time(date);
		transactions.setPatronId(patron.getPatronId());
		transactions.setBalance(patron.getBalance());
		
		prevTransaction = transactionsRepository.findByPatronIdAndMovieId(patron.getPatronId(), transactions.getMovieId());
		if(prevTransaction != null){
			transactions.setTransactionsId(prevTransaction.getTransactionsId());
		}
		
		/**Setting data in ratio table*/
		ratio = ratioService.getRatioByMovieId(transactions.getMovieId());
		if (ratio == null) {
			ratio = new Ratio();
			ratio.setMovieId(transactions.getMovieId());
			ratio.setHero(movie.getHero());
			ratio.setHeroin(movie.getHeroin());
			if (transactions.getFavorite().equalsIgnoreCase(movie.getHero())) {
				ratio.setHeroTotalTickets(transactions.getTicket());
				ratio.setHeroRatio(transactions.getTicket());
				ratio.setHeroinRatio(0);
			} else {
				ratio.setHeroinTotalTickets(transactions.getTicket());
				ratio.setHeroinRatio(transactions.getTicket());
				ratio.setHeroRatio(0);
			}
		} else {
			if(prevTransaction != null){
				if (ratio.getHero().equalsIgnoreCase(prevTransaction.getFavorite())) {
					ratio.setHeroTotalTickets(ratio.getHeroTotalTickets() - prevTransaction.getTicket());
				}else{
					ratio.setHeroinTotalTickets(ratio.getHeroinTotalTickets() - prevTransaction.getTicket());
				}
			}
			if (transactions.getFavorite().equalsIgnoreCase(movie.getHero())) {
				ratio.setHeroTotalTickets(ratio.getHeroTotalTickets() + transactions.getTicket());
				ratio.setHeroRatio(ratio.getHeroinTotalTickets() / ratio.getHeroTotalTickets());
				if (ratio.getHeroinTotalTickets() > 0)
					ratio.setHeroinRatio(ratio.getHeroTotalTickets() / ratio.getHeroinTotalTickets());
			} else {
				ratio.setHeroinTotalTickets(ratio.getHeroinTotalTickets() + transactions.getTicket());
				ratio.setHeroinRatio(ratio.getHeroTotalTickets() / ratio.getHeroinTotalTickets());
				if (ratio.getHeroTotalTickets() > 0)
					ratio.setHeroRatio(ratio.getHeroinTotalTickets() / ratio.getHeroTotalTickets());
			}
		}
		transactionsRepository.save(transactions);
		ratioService.save(ratio);
		return getCurrentDateMovies();
	}
	
	public void up1(Transactions results){

		Patrons patron = null;
		Ratio ratio = null;
		Movies movie = null;
		
		/**Validating patron*/
		patron = patronsService.getPatron(results.getUsername(), results.getPassword());
		if (patron == null)
			throw new BadCredentialsException("username and password are not matched!!");
		if (patron.getRole() != 1)
			throw new BadCredentialsException("Access Denied !!");
		
		/**Calculating  wining ratio*/
		double winnigRatio=0;
		ratio = ratioService.getRatioByMovieId(results.getMovieId());
		if(ratio.getHero().equalsIgnoreCase(results.getAward()))
			winnigRatio = ratio.getHeroRatio();
		else
			winnigRatio = ratio.getHeroinRatio();
		
		/**updating  movie table with awarded hero*/
		movie = moviesService.getMovieByMovieId(results.getMovieId());
		movie.setAward(null);
		moviesService.save(movie);
		
		/**updating  transaction and patron tables with balances*/
		ArrayList<Transactions>  transactionList = getTransactionsByMovieId(results.getMovieId());
		for(Transactions transaction:transactionList){
			patron = patronsService.getPatron(transaction.getPatronId());
			if(transaction.getFavorite().equalsIgnoreCase(results.getAward())){
				transaction.setBalance(0);
				patron.setBalance(patron.getBalance()-(transaction.getTicket()*winnigRatio));
			}else{
				transaction.setBalance(0);
				patron.setBalance(patron.getBalance()+transaction.getTicket());
			}
			transaction.setAward(null);
			transactionsRepository.save(transaction);
			patronsService.update(patron);
		}
	}

	public HashMap<String, ArrayList<Results>> update(Transactions results) {
		Patrons patron = null;
		Ratio ratio = null;
		Movies movie = null;
		
		/**Validating patron*/
		patron = patronsService.getPatron(results.getUsername(), results.getPassword());
		if (patron == null)
			throw new BadCredentialsException("username and password are not matched!!");
		if (patron.getRole() != 1)
			throw new BadCredentialsException("Access Denied !!");
		
		/**Calculating  wining ratio*/
		double winnigRatio=0;
		ratio = ratioService.getRatioByMovieId(results.getMovieId());
		if(ratio.getHero().equalsIgnoreCase(results.getAward()))
			winnigRatio = ratio.getHeroRatio();
		else
			winnigRatio = ratio.getHeroinRatio();
		
		/**updating  movie table with awarded hero*/
		movie = moviesService.getMovieByMovieId(results.getMovieId());
		movie.setAward(results.getAward());
		moviesService.save(movie);
		
		/**updating  transaction and patron tables with balances*/
		ArrayList<Transactions>  transactionList = getTransactionsByMovieId(results.getMovieId());
		for(Transactions transaction:transactionList){
			patron = patronsService.getPatron(transaction.getPatronId());
			if(transaction.getFavorite().equalsIgnoreCase(results.getAward())){
				transaction.setBalance(transaction.getTicket()*winnigRatio);
				patron.setBalance(patron.getBalance()+transaction.getBalance());
			}else{
				transaction.setBalance(-transaction.getTicket());
				patron.setBalance(patron.getBalance()-transaction.getTicket());
			}
			transaction.setAward(results.getAward());
			transactionsRepository.save(transaction);
			patronsService.update(patron);
		}
		return getAllTransactionsByUsers();
	}
	
	public ArrayList<Transactions> getTransactionsByPatronId(String username, String password){
		Patrons patrons = patronsService.getPatron(username, password);
		return (ArrayList<Transactions>) transactionsRepository.findByPatronId(patrons.getPatronId());
	}
	
	public ArrayList<Transactions> getTransactionsByMovieId(int movieId){
		return (ArrayList<Transactions>) transactionsRepository.findByMovieId(movieId);
	}
	
	public ArrayList<Transactions> getAllTransactions(){
		return (ArrayList<Transactions>) transactionsRepository.findAllByOrderByTransactionsIdAsc();
	}
	
	public HashMap<String, ArrayList<Transactions>> getTransactionsByUsers(){
		HashMap<String, ArrayList<Transactions>> res = new HashMap<String, ArrayList<Transactions>>();
		ArrayList<Patrons> patrons = (ArrayList<Patrons>) patronsService.getAll();
		for(Patrons patron: patrons){
			res.put(patron.getFull_name(), (ArrayList<Transactions>) transactionsRepository.findByPatronId(patron.getPatronId()));
		}
		return res;
	}
	
	
	public HashMap<Integer, ArrayList<Results>> getCurrentDateMovies(){
		HashMap<Integer, ArrayList<Results>> res = new HashMap<Integer, ArrayList<Results>>();
		String date = Util.getDate("dd/M/yyyy");
		ArrayList<Movies> movieList=moviesService.getByDate(date);
		for(Movies movie:movieList){
			ArrayList<Results> resList = new ArrayList<>();
			ArrayList<Transactions> transList = (ArrayList<Transactions>) transactionsRepository.findByMovieId(movie.getMovieId());
			for(Transactions transactions:transList){
				Patrons patron = patronsService.getPatron(transactions.getPatronId());
				Ratio ratio = ratioService.getRatioByMovieId(movie.getMovieId());
				Results results = new Results();
				results.setHero(movie.getHero());
				results.setHeroin(movie.getHeroin());
				results.setTheater(movie.getTheater());
				results.setFavorite(transactions.getFavorite());
				results.setTicket(transactions.getTicket());
				results.setPatron(patron.getUsername());
				results.setMovieDate(movie.getDate());
				results.setTransactionDate(transactions.getDate_time());
				results.setHeroRatio(ratio.getHeroRatio());
				results.setHeroinRatio(ratio.getHeroinRatio());
				results.setHeroTotalAmount(ratio.getHeroTotalTickets());
				results.setHeroinTotalAmount(ratio.getHeroinTotalTickets());
				resList.add(results);
			}
			res.put(movie.getMovieId(), resList);
		}
		return res;
	}
	
	public HashMap<String, ArrayList<Results>> getAllTransactionsByUsers(){
		HashMap<String, ArrayList<Results>> res = new HashMap<String, ArrayList<Results>>();
		ArrayList<Patrons> patrons = (ArrayList<Patrons>) patronsService.getAll();
		for(Patrons patron: patrons){
			ArrayList<Results> resultsList = new ArrayList<Results>();
			for(Transactions transactions : transactionsRepository.findByPatronId(patron.getPatronId())){
				Results results = new Results();
				Movies movie = moviesService.getMovieByMovieId(transactions.getMovieId());
				Ratio ratio = ratioService.getRatioByMovieId(transactions.getMovieId());
				double winnigRatio=0;
				double earned=0;
				if(transactions.getAward() != null){
					if(ratio.getHero().equalsIgnoreCase(transactions.getAward()))
						winnigRatio = ratio.getHeroRatio();
					else
						winnigRatio = ratio.getHeroinRatio();
					
					if(transactions.getFavorite().equalsIgnoreCase(transactions.getAward()))
						earned=Double.parseDouble(String.format("%.2f", transactions.getTicket()*winnigRatio));
					else
						earned=-transactions.getTicket();
				}
				//results.setTranactionId(transactions.getTransactionsId());
				results.setHero(movie.getHero());
				results.setHeroin(movie.getHeroin());
				results.setTheater(movie.getTheater());
				results.setFavorite(transactions.getFavorite());
				results.setTicket(transactions.getTicket());
				results.setEarned(earned);
				results.setBalance(patron.getBalance());
				results.setAward(transactions.getAward());
				//results.setPatron(patron.getUsername());
				results.setMovieDate(movie.getDate());
				results.setTransactionDate(transactions.getDate_time());
				results.setHeroRatio(ratio.getHeroRatio());
				results.setHeroinRatio(ratio.getHeroinRatio());
				results.setHeroTotalAmount(ratio.getHeroTotalTickets());
				results.setHeroinTotalAmount(ratio.getHeroinTotalTickets());
				resultsList.add(results);
			}
			res.put(patron.getFull_name(), resultsList);
		}
		return res;
	}
	
	public void deleteAll(){
		transactionsRepository.deleteAll();
		ratioService.deleteAll();
	}
}
