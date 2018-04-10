package com.cinema.festival.model;

import org.springframework.stereotype.Component;

@Component
public class Results {
	private int tranactionId;
	private String hero;
	private String heroin;
	private String theater;
	private String favorite;
	private int ticket;
	private double earned;
	private double balance;
	private String award;
	private String patron;
	private String transactionDate;
	private String movieDate;
	private double heroRatio;
	private double heroinRatio;
	private double heroTotalAmount;
	private double heroinTotalAmount;
	
	public int getTranactionId() {
		return tranactionId;
	}
	public void setTranactionId(int tranactionId) {
		this.tranactionId = tranactionId;
	}
	public String getHero() {
		return hero;
	}
	public void setHero(String hero) {
		this.hero = hero;
	}
	public String getHeroin() {
		return heroin;
	}
	public void setHeroin(String heroin) {
		this.heroin = heroin;
	}
	public String getTheater() {
		return theater;
	}
	public void setTheater(String theater) {
		this.theater = theater;
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
	public double getEarned() {
		return earned;
	}
	public void setEarned(double earned) {
		this.earned = earned;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAward() {
		return award;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public String getPatron() {
		return patron;
	}
	public void setPatron(String patron) {
		this.patron = patron;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getMovieDate() {
		return movieDate;
	}
	public void setMovieDate(String movieDate) {
		this.movieDate = movieDate;
	}
	public double getHeroRatio() {
		return heroRatio;
	}
	public void setHeroRatio(double heroRatio) {
		this.heroRatio = heroRatio;
	}
	public double getHeroinRatio() {
		return heroinRatio;
	}
	public void setHeroinRatio(double heroinRatio) {
		this.heroinRatio = heroinRatio;
	}
	public double getHeroTotalAmount() {
		return heroTotalAmount;
	}
	public void setHeroTotalAmount(double heroTotalAmount) {
		this.heroTotalAmount = heroTotalAmount;
	}
	public double getHeroinTotalAmount() {
		return heroinTotalAmount;
	}
	public void setHeroinTotalAmount(double heroinTotalAmount) {
		this.heroinTotalAmount = heroinTotalAmount;
	}
}
