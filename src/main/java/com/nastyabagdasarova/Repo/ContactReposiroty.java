package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Contact;

@Repository
public interface ContactReposiroty extends CrudRepository<Contact, Long> {
	Contact findById(int id);
	 
}
