package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.Model.VideoQR;
import com.nastyabagdasarova.Repo.VideoQRRepository;

@Service
public class VideoQRService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	@Autowired 
	private VideoQRRepository videoQRRepository;

	
	public List<VideoQR> listAll(){
		return (List<VideoQR>) videoQRRepository.findAll();
	}
	
	public VideoQR save(VideoQR videoQR) {
		return videoQRRepository.save(videoQR);
	}
	
	public VideoQR findById(int id) {
		return videoQRRepository.findById(id);
	}
	
	public VideoQR findByIdOfContentOrReturnNull(int id_of_user, int id_of_video) {

		entityManager = entityManagerFactory.createEntityManager();
		Query<VideoQR> query = (Query<VideoQR>) entityManager.createQuery("select p from VideoQR p "
		   		+ " where p.id_of_video = :id_of_video and p.id_of_user = :id_of_user ", VideoQR.class);
	    query.setParameter("id_of_user", id_of_user);
	    query.setParameter("id_of_video", id_of_video);
		List<VideoQR> resultList = (List<VideoQR>) query.getResultList();
		if(resultList == null || resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}
	
	public boolean isQueueFreeForConvert() {
		entityManager = entityManagerFactory.createEntityManager();
		
		Query<VideoQR> query = (Query<VideoQR>) entityManager.createQuery("select p from VideoQR p "
		   		+ " where p.status = '1' ", VideoQR.class);
		List<VideoQR> resultList = (List<VideoQR>) query.getResultList();
		if(resultList == null || resultList.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	
}
