package com.cinema.festival.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ratio")
public class Ratio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="my_entity_seq_gen")
	@SequenceGenerator(name="my_entity_seq_gen", sequenceName="MY_ENTITY_SEQ")
	private int ratio_id;
	private int movieId;
	private String hero;
	private String heroin;
	private double heroTotalTickets;
	private double heroinTotalTickets;
	private double heroRatio;
	private double heroinRatio;
	
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
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public double getHeroTotalTickets() {
		return heroTotalTickets;
	}
	public void setHeroTotalTickets(double heroTotalTickets) {
		this.heroTotalTickets = heroTotalTickets;
	}
	public double getHeroinTotalTickets() {
		return heroinTotalTickets;
	}
	public void setHeroinTotalTickets(double heroinTotalTickets) {
		this.heroinTotalTickets = heroinTotalTickets;
	}
	public double getHeroRatio() {
		return Double.parseDouble(String.format("%.2f", heroRatio));
	}
	public void setHeroRatio(double heroRatio) {
		this.heroRatio = heroRatio;
	}
	public double getHeroinRatio() {
		return Double.parseDouble(String.format("%.2f", heroinRatio));
	}
	public void setHeroinRatio(double heroinRatio) {
		this.heroinRatio = heroinRatio;
	}
	public int getRatio_id() {
		return ratio_id;
	}
	public void setRatio_id(int ratio_id) {
		this.ratio_id = ratio_id;
	}

}
