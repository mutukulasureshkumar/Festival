package com.cinema.festival.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.cinema.festival.model.Msg9;
import com.cinema.festival.model.SMSMsg9;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Msg9Service {

	public String sendSMS() {
		String output = null;
		HashMap<String, String> contacts = new HashMap<String, String>();
		contacts.put("Bharat", "+919885958003");
		contacts.put("Mahesh", "+917769040088");
		contacts.put("Ravi", "+919168420099");
		contacts.put("Sudheer", "+919000639878");
		contacts.put("Surya", "+919948219490");
		contacts.put("Tejo", "+917769963322");
		contacts.put("Suresh", "+917769050088");

		ArrayList<SMSMsg9> smsMsg9sList = new ArrayList<SMSMsg9>();
		for (String key : contacts.keySet()) {
			SMSMsg9 smsMsg9 = new SMSMsg9();
			smsMsg9.setMessage("Alert!! Hello " + key
					+ ", last one hour for today guess. Please post your guess now and avoid posting default 5 points on Team 1");
			ArrayList<String> mobile = new ArrayList<String>();
			mobile.add(contacts.get(key));
			smsMsg9.setTo(mobile);
			smsMsg9sList.add(smsMsg9);
		}

		Msg9 message = new Msg9();
		message.setCountry("91");
		message.setRoute("4");
		message.setSender("HardRockies");
		message.setSms(smsMsg9sList);

		try {
			URL url = new URL("http://api.msg91.com/api/v2/sendsms");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("authkey", "209526AkqpckEQN5ace1763");

			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValueAsString(message);

			String input = mapper.writeValueAsString(message);
			System.out.println("input :: \n" + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("output :: \n" + output);
		return output;

	}
}
