package com.nastyabagdasarova.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Paymentapp;
import com.nastyabagdasarova.Repo.PaymentRepository;

@Service
public class PaymentService {

	@Autowired 
	private PaymentRepository paymentRepository;

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");
	
	public List<Paymentapp> listAll(){
		return (List<Paymentapp>) paymentRepository.findAll();
	}
	public Paymentapp findById(Integer id) {
		Optional<Paymentapp> paymentappOpt = paymentRepository.findById(id);
		if(paymentappOpt.isPresent()) {
			return paymentappOpt.get();
		}
		return null;
	}
	public Paymentapp save(Paymentapp payment) {
		return paymentRepository.save(payment);
	}
	public Paymentapp findByIdNotPayed(int id) {
		entityManager = entityManagerFactory.createEntityManager();
		Query<Paymentapp> query = (Query<Paymentapp>) entityManager.createQuery("select p from Paymentapp p "
		   		+ " where p.id = :id and p.status = 0", Paymentapp.class);
	    query.setParameter("id", id);
		List<Paymentapp> resultList = (List<Paymentapp>) query.getResultList();
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	public Paymentapp findByIdOfUser(int id_of_user) {

		entityManager = entityManagerFactory.createEntityManager();
		Query<Paymentapp> query = (Query<Paymentapp>) entityManager.createQuery("select p from Paymentapp p "
		   		+ " where p.id_of_user = :id_of_user and p.status = 0", Paymentapp.class);
	    query.setParameter("id_of_user", id_of_user);
		List<Paymentapp> resultList = (List<Paymentapp>) query.getResultList();
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	public Paymentapp findByPaypalPaymentIdOrReturnNull(int id_of_user, String paypal_payment_id) {

		entityManager = entityManagerFactory.createEntityManager();
		Query<Paymentapp> query = (Query<Paymentapp>) entityManager.createQuery("select p from Paymentapp p "
		   		+ " where p.id_of_user = :id_of_user"
		   		+ " and paypal_payment_id = :paypal_payment_id and p.status = 0 ORDER BY id", Paymentapp.class);
	    query.setParameter("id_of_user", id_of_user);
	    query.setParameter("paypal_payment_id", paypal_payment_id);
	    query.setMaxResults(1);
	    
		return query.getSingleResult();
	}
}
