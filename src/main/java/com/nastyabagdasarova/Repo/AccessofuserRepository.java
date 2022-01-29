package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Accessofuser;

@Repository
public interface AccessofuserRepository extends CrudRepository<Accessofuser, Integer> {

	//AccessOfUser selectActiveAccessOfUser(int id_of_user);
}
