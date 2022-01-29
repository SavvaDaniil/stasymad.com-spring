package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Back;
import com.nastyabagdasarova.Model.Cart;
import com.nastyabagdasarova.Repo.BackRepository;

@Service
public class BackService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	@Autowired 
	private BackRepository backRepository;
	
	public List<Back> listAll(){
		return (List<Back>) backRepository.findAll();
	}
	public Back findById(int id) {
		return backRepository.findById(id);
	}
	
	public Back findByIdOfContent(int id_of_course) {

		entityManager = entityManagerFactory.createEntityManager();
		Query<Back> query = (Query<Back>) entityManager.createQuery("select p from Back p"
		   		+ " where p.id_of_course = :id_of_course", Back.class);
	    query.setParameter("id_of_course", id_of_course);
		List<Back> resultList = (List<Back>) query.getResultList();
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
}
