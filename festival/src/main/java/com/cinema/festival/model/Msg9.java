package com.cinema.festival.model;

import java.util.ArrayList;

public class Msg9 {
	private String sender;
	private String route;
	private String country;
	private ArrayList<SMSMsg9> sms;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public ArrayList<SMSMsg9> getSms() {
		return sms;
	}
	public void setSms(ArrayList<SMSMsg9> sms) {
		this.sms = sms;
	}
}
