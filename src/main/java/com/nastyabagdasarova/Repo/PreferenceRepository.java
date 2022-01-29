package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Preference;

@Repository
public interface PreferenceRepository extends CrudRepository<Preference, Integer> {
	Preference findById(int id);
}
