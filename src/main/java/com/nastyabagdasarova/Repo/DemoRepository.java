package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nastyabagdasarova.Model.Demo;

@Repository
public interface DemoRepository extends CrudRepository<Demo, Integer> {
	public Demo findById(int id);
}
