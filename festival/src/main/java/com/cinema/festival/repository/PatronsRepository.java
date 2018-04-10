package com.cinema.festival.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.festival.model.Patrons;


@Repository
public interface PatronsRepository extends JpaRepository<Patrons, Integer>{
	public Patrons findByUsernameAndPassword(String username, String password);
	public Patrons findByPatronId(int patron_id);
}
