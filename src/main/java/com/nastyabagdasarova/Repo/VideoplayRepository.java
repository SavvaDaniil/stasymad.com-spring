package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Videoplay;

@Repository
public interface VideoplayRepository extends CrudRepository<Videoplay, Long> {
	public Videoplay findById(int id);
}
