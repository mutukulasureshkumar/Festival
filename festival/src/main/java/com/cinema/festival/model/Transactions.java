package com.cinema.festival.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="transactions")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="MY_ENTITY_SEQ")	
	private long transactionsId;
	private int patronId;
	private int movieId;
	private String favorite;
	private int ticket;
	private double balance;
	private String dateTime;
	@Transient
	private String username;
	@Transient
	private String password;
	
	private String award;
	
	public long getTransactionsId() {
		return transactionsId;
	}
	public void setTransactionsId(long transactionId) {
		this.transactionsId = transactionId;
	}
	public int getPatronId() {
		return patronId;
	}
	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	public int getTicket() {
		return ticket;
	}
	public void setTicket(int ticket) {
		this.ticket = ticket;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDate_time() {
		return dateTime;
	}
	public void setDate_time(String date_time) {
		this.dateTime = date_time;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	
	public String toString(){
		return "transactionsId :: "+transactionsId+"\n"+
				"patronId :: "+patronId+"\n"+
				"movieId :: "+movieId+"\n"+
				"favorite :: "+favorite+"\n"+
				"ticket :: "+ticket+"\n"+
				"balance :: "+balance+"\n"+
				"date_time :: "+dateTime;
	}
}
