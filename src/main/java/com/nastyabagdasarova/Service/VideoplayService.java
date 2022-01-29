package com.nastyabagdasarova.Service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Videoplay;
import com.nastyabagdasarova.Repo.VideoplayRepository;

@Service
public class VideoplayService {


	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	
	@Autowired 
	private VideoplayRepository videoplayRepository;
	
	public Videoplay findById(int id) {
		return videoplayRepository.findById(id);
	}
	
	public List<Videoplay> listAll(){
		return (List<Videoplay>) videoplayRepository.findAll();
	}
	
	public Videoplay save(Videoplay videoplay) {
		return videoplayRepository.save(videoplay);
	}
	
	
	
	public Videoplay findByIdOfContent(int id_of_course, int id_of_user) {
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Videoplay> query = (Query<Videoplay>) entityManager.createQuery("SELECT p FROM Videoplay p "
			+ " WHERE p.id_of_course = :id_of_course AND "
	   		+ " p.id_of_user = :id_of_user", Videoplay.class);
	   query.setParameter("id_of_course", id_of_course);
	   query.setParameter("id_of_user", id_of_user);
	   List<Videoplay> resultList = (List<Videoplay>) query.getResultList();
	   
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	
	
	
	
	
}
