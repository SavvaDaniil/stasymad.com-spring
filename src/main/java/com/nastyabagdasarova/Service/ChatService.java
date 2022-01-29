package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Chat;
import com.nastyabagdasarova.Repo.ChatRepository;

@Service
public class ChatService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	@Autowired 
	private ChatRepository chatRepository;

	public List<Chat> listAll(){
		return (List<Chat>) chatRepository.findAll();
	}
	public Chat findById(int id) {
		return chatRepository.findById(id);
	}
	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}
	
	
	public List<Chat> findByIdOfBack(int id_of_user, int id_of_back, int default0_unread1) {

		entityManager = entityManagerFactory.createEntityManager();
		Query<Chat> query = null;
		if(default0_unread1 == 0) {
			query = (Query<Chat>) entityManager.createQuery("SELECT p FROM Chat p "
				+ " WHERE p.id_of_back = :id_of_back "
		   		+ " AND (p.from_who = :id_of_user OR p.to_who = :id_of_user)", Chat.class);
		}
		if(default0_unread1 == 1) {
			query = (Query<Chat>) entityManager.createQuery("SELECT p FROM Chat p "
					+ " WHERE p.id_of_back = :id_of_back AND date_of_readed IS NULL"
			   		+ " AND (p.from_who = :id_of_user OR p.to_who = :id_of_user)", Chat.class);
		}
	   query.setParameter("id_of_back", id_of_back);
	   query.setParameter("id_of_user", id_of_user);
	   List<Chat> resultList = (List<Chat>) query.getResultList();
	   
		return resultList;
	}
	
}
