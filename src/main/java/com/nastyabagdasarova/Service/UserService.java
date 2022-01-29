package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Repo.UserRepository;

@Service
public class UserService {

	@PersistenceContext
	EntityManager entityManager;
    
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");
    
    
	@Autowired 
	private UserRepository userRepository;
	
	public List<User> listAll(){
		return (List<User>) userRepository.findAll();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	
	
	
	
	public boolean isAlreadyExistUsername(Integer currentIdOfUser, String newUsername){

		entityManager = entityManagerFactory.createEntityManager();
		Query<User> query = (Query<User>) entityManager.createQuery("select p from User p "
	   		+ " where p.id != :id_of_user AND p.username = :newusername", User.class);
	   query.setParameter("id_of_user", currentIdOfUser);
	   query.setParameter("newusername", newUsername);
	   
	   List<User> resultList = (List<User>) query.getResultList();
		
	   if(resultList.isEmpty()) {
		   return false;
	   } else {
		   return true;
	   }
	}
}
