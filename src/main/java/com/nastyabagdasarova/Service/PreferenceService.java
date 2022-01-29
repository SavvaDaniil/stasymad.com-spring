package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Preference;
import com.nastyabagdasarova.Repo.PreferenceRepository;

@Service
public class PreferenceService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");
	
	@Autowired
	PreferenceRepository preferenceRepository;

	
	public List<Preference> listAll(){
		return (List<Preference>) preferenceRepository.findAll();
	}
	
	public Preference findById(int id) {
		return preferenceRepository.findById(id);
	}
	
	public String isUseAutoVideoQRGenerator() {
		return findByName("useAutoVideoQRGenerator");
	}
	
	public String isUseQRContent() {
		return findByName("useQRContent");
	}
	
	
	private String findByName(String name) {
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Preference> query = (Query<Preference>) entityManager.createQuery("SELECT p FROM Preference p "
			+ " WHERE p.name = :name", Preference.class);
		query.setParameter("name", name);
		List<Preference> resultList = (List<Preference>) query.getResultList();
	   
		if(resultList.isEmpty()) {
			query = null;
			resultList = null;
			System.gc();
			return null;
		}
		return resultList.get(0).getValue();
	}
}
