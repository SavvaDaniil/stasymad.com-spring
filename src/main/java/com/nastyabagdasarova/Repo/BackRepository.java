package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Back;

@Repository
public interface BackRepository extends CrudRepository<Back, Integer> {
	public Back findById(int id);
}
