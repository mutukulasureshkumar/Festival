package com.cinema.festival.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TextLocalSMS {
	public String sendSms(String name, String mobile) {
		System.out.println("******************SMS Service Triggered******************* :: "+ new Date());
		System.out.println("Name :: "+ name +" Number :: "+ mobile);
		try {
			// Construct data
			String apiKey = "apikey=" + "8mG6stnxB8E-JhV6nFbEXylBTd6O28Zb0UyoEvQ8Q4";
			String message = "&message=" + "Alert!! Hello "+name+", Last one hour for today guess. Please post your guess now and avoid posting default 5 points on Team 1";
			String sender = "&sender=" + "HardRokies Team";
			String numbers = "&numbers=" + mobile.trim();

			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			return stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error "+e;
		}
	}
}

