package com.cinema.festival.model;

import java.util.ArrayList;

public class SMSMsg9 {
	
		private String message;
		private ArrayList<String> to = new ArrayList<String>();

		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public ArrayList<String> getTo() {
			return to;
		}
		public void setTo(ArrayList<String> to) {
			this.to = to;
		}

}
