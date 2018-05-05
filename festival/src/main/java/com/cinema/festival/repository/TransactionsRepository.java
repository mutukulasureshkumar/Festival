package com.cinema.festival.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.festival.model.Transactions;


@Repository
public interface TransactionsRepository  extends JpaRepository<Transactions, Integer>{
	public List<Transactions> findByMovieId(int movieId);
	public List<Transactions> findByPatronId(int patronId);
	public Transactions findByPatronIdAndMovieId(int patronId, int movieId);
	public List<Transactions> findAllByOrderByTransactionsIdAsc();
	public List<Transactions> findByPatronIdAndDateTimeLessThan(int patronId,String dateTime);
	public List<Transactions> findByPatronIdAndMovieIdNotIn(int patronId,Collection<Integer> ids);
	public void deleteByMovieId(int movie_id);
}
