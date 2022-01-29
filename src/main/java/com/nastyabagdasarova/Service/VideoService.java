package com.nastyabagdasarova.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.AbstractFactory.ListOfSubtitlesAbstractFactory;
import com.nastyabagdasarova.Factory.VideoQRFactory;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.Video;
import com.nastyabagdasarova.Model.VideoQR;
import com.nastyabagdasarova.Repo.VideoRepository;

@Service
public class VideoService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	
	@Autowired 
	private VideoRepository videoRepository;
	
	
	public List<Video> listAll(){
		return (List<Video>) videoRepository.findAll();
	}
	public Video findById(int id) {
		return videoRepository.findById(id);
	}
	
	public Video save(Video video) {
		return videoRepository.save(video);
	}
	
	
	
	public List<Video> findAllByIdOfContent(int id_of_course, User user) {
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Video> query = (Query<Video>) entityManager.createQuery("select p from Video p "
				+ "INNER JOIN Accessofuser a ON a.id_of_course = p.id_of_course and a.id_of_user =:id_of_user and a.back = 0 "
		   		+ " where p.id_of_course = :id_of_course and p.status = 1", Video.class);
	    query.setParameter("id_of_user", user.getId());
	    query.setParameter("id_of_course", id_of_course);
		List<Video> resultList = (List<Video>) query.getResultList();
		
		ListOfSubtitlesAbstractFactory listOfSubtitleAbstractFactory = new ListOfSubtitlesAbstractFactory();
		//VideoQR videoQR;
		//VideoQRFactory videoQRFactory = new VideoQRFactory();
		int i = 0;
		for(Video video : resultList) {
			//queueOfSubtitle = queueOfSubtitleAbstractFactory.create(video.getId(), 1);
			//video.setQueueOfSubtitle(queueOfSubtitle);
			//resultList.set(i, video);
			
			video.setSrc(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/playlist.m3u8");
			
			//для доступа к видео по videoqr
			video.setSrcOutput(video.getBasePath() + "/" + video.getId() + "_" + video.getHash() + "/output.mp4");
			
			
			
			
			video.setListOfSubtitle(listOfSubtitleAbstractFactory.create(video.getId(), 1).getSubtitles());
			resultList.set(i, video);
			i++;
		}
		//videoQRFactory = null;
		//videoQR = null;
		
		query = null;
		listOfSubtitleAbstractFactory = null;
		System.gc();
		
		
		return resultList;
	}
}
