package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.Cart;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Repo.CartRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");
	
	@Autowired 
	private CartRepository cartRepository;
	
	public List<Cart> listAll(){
		return (List<Cart>) cartRepository.findAll();
	}
	public Cart save(Cart cart) {
		return cartRepository.save(cart);
	}
	public void deleteById(int id) {
		cartRepository.deleteById(id);
	}
	
	public Cart getContentById(int id_of_user, int id_of_course, int back, int operation) {
		entityManager = entityManagerFactory.createEntityManager();
		Query<Cart> query = (Query<Cart>) entityManager.createQuery("select p from Cart p "
		   		+ " where p.id_of_user = :id_of_user AND p.id_of_course = :id_of_course "
		   				+ "AND p.is_back = :back AND p.status = 0", Cart.class);
	    query.setParameter("id_of_user", id_of_user);
	    query.setParameter("id_of_course", id_of_course);
	    query.setParameter("back", back);
		List<Cart> resultList = (List<Cart>) query.getResultList();
		if(resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	

	public List<Cart> getCartOfUser(int id_of_user) {
		entityManager = entityManagerFactory.createEntityManager();
		Query<Cart> query = (Query<Cart>) entityManager.createQuery("select p from Cart p "
		   		+ " where p.id_of_user = :id_of_user AND p.status = 0", Cart.class);
	    query.setParameter("id_of_user", id_of_user);
		List<Cart> resultList = (List<Cart>) query.getResultList();
		
		return resultList;
	}
	
	public List<Cart> getCartOfIdOfPayment(int id_of_payment) {
		entityManager = entityManagerFactory.createEntityManager();
		Query<Cart> query = (Query<Cart>) entityManager.createQuery("select p from Cart p "
		   		+ " where p.id_of_payment = :id_of_payment AND p.status = 0", Cart.class);
	    query.setParameter("id_of_payment", id_of_payment);
		List<Cart> resultList = (List<Cart>) query.getResultList();
		
		return resultList;
	}
	

}
