package com.nastyabagdasarova.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Model.Contact;
import com.nastyabagdasarova.Repo.ContactReposiroty;

@Service
public class ContactService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");


	@Autowired 
	private ContactReposiroty contactRepository;
	

	public List<Contact> listAll(){
		return (List<Contact>) contactRepository.findAll();
	}
	public Contact findById(int id) {
		return contactRepository.findById(id);
	}
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}
	public void delete(Contact contact) {
		contactRepository.delete(contact);
	}
	
	
	public Integer countByDay() {
		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		Date day_of_add = ptsc.parseDatestamp(timestamp);
		System.out.println("day_of_add = " + day_of_add);

		entityManager = entityManagerFactory.createEntityManager();
		Query<Contact> query = (Query<Contact>) entityManager.createQuery("select p from Contact p "
		   	+ " where p.day_of_add = :day_of_add", Contact.class);
	    query.setParameter("day_of_add", day_of_add);
	    
	    timestamp = null;
	    ptsc = null;
	    day_of_add = null;
	    System.gc();
		
		return query.getResultList().size();
	}
	
}
