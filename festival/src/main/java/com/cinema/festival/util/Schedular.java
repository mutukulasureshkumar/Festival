package com.cinema.festival.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cinema.festival.service.TransactionsService;

@Component
public class Schedular {
	
	/*@Autowired
	private TextLocalSMS sms;*/
	
	@Autowired
	private TransactionsService transactionsService;
	
	/*@Scheduled(cron="0 00 13 * * *", zone="Asia/Calcutta")
	public void sendAlertTL(){
		try{
			System.out.println("******************ALERT Schedular Triggered******************** :: "+ new Date());
			HashMap<String, String> contacts = new HashMap<String, String>();
			contacts.put("Bharat", "+919885958003");
			contacts.put("Mahesh", "+917769040088");
			contacts.put("Ravi", "+919168420099");
			contacts.put("Sudheer", "+919000639878");
			contacts.put("Surya", "+919948219490");
			contacts.put("Tejo", "+917769963322");
			contacts.put("Suresh", "+917769050088");
			contacts.forEach((k,v)->System.out.println(sms.sendSms(k, v)));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 20 21 * * *", zone="Asia/Calcutta")
	public void sendAlertM9(){
		System.out.println("******************Schedular Triggered******************* :: "+ new Date());
		Msg9Service msg9Service = new Msg9Service();
		msg9Service.sendSMS();
	}*/
	

	@Scheduled(cron="0 0 14 * * *", zone="Asia/Calcutta")
	public void defaultGuess(){
		System.out.println("******************DEFAULT Schedular Triggered******************* :: "+ new Date());
		try{
			transactionsService.defaultGuess();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
