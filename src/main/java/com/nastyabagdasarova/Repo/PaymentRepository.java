package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Paymentapp;

@Repository
public interface PaymentRepository extends CrudRepository<Paymentapp, Integer> {
	Paymentapp findById(int id);
}
