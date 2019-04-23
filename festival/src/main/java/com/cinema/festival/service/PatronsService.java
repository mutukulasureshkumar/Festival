package com.cinema.festival.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.festival.model.Patrons;
import com.cinema.festival.repository.PatronsRepository;
import com.cinema.festival.util.Util;

@Service
@Transactional
public class PatronsService {
	
	@Autowired
	private PatronsRepository patronRepository;
	
	public Patrons save(Patrons patrons){
		String date = Util.getDate("dd/M/yyyy  hh:mm:ss");
		patrons.setDate_time(date);
		patrons.setStatus("ACTIVE");
		if(patrons.getPatronId() > 0){
			Patrons oldPatrons = getPatron(patrons.getPatronId());
			if(oldPatrons != null){
				patrons.setAmount(patrons.getAmount()+oldPatrons.getAmount());
				patrons.setBalance(patrons.getBalance()+oldPatrons.getBalance());
			}
		}
		return patronRepository.save(patrons);
	}
	
	public Patrons getPatron(String username, String password){
		return patronRepository.findByUsernameAndPassword(username, password);
	}
	
	public Patrons getPatron(int patron_id){
		return patronRepository.findByPatronId(patron_id);
	}
	
	public Patrons update(Patrons patrons){
		return patronRepository.save(patrons);
	}

	public List<Patrons> getAll(){
		return patronRepository.findAll();
	}
	
	public void deletePatron(Patrons patron){
		patronRepository.delete(patron);
	}
	
	public double checkBalance(){
		double sum=0;
		ArrayList<Patrons> patrons = (ArrayList<Patrons>) patronRepository.findAll();
		for(Patrons patron:patrons){
			sum=sum+patron.getBalance();
		}
		return sum;
	}

	public Patrons updateBalance(String username, double balance){
		Patrons patrons = new Patrons();
		patrons = patronRepository.findByUsername(username);
		System.out.print("Requested for update the balance by :: "+username+", of amount :: "+balance+". Previous balance :: "+patrons.getBalance()+". Updated Balace :: "+(patrons.getBalance()+balance)+". ");
		patrons.setBalance(patrons.getBalance()+balance);
		Patrons updatedPatron = patronRepository.save(patrons);
		if(updatedPatron != null){
			System.out.println("Transaction is Successfull!!!");
		}else{
			System.out.println("Transaction is Failed!!!");
		}
		return updatedPatron;
	}
}
