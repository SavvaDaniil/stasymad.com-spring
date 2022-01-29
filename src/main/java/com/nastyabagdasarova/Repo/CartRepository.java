package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nastyabagdasarova.Model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

}
