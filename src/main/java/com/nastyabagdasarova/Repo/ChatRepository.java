package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {
	public Chat findById(int id);
}
