package com.nastyabagdasarova.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.AbstractFactory.ListOfSubtitlesAbstractFactory;
import com.nastyabagdasarova.Model.Demo;
import com.nastyabagdasarova.Model.ListOfSubtitles;
import com.nastyabagdasarova.Model.Subtitle;
import com.nastyabagdasarova.Repo.DemoRepository;

@Service
public class DemoService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");

	@Autowired 
	private DemoRepository demoRepository;
	

	public List<Demo> listAll(){
		return (List<Demo>) demoRepository.findAll();
	}
	
	public Demo findById(int id) {
		return demoRepository.findById(id);
	}
	
	public Demo findByIdOfCourse(int id_of_course, boolean withSubtitle) {
		
		entityManager = entityManagerFactory.createEntityManager();
		Query<Demo> query = (Query<Demo>) entityManager.createQuery("SELECT p FROM Demo p "
			+ " WHERE p.id_of_course = :id_of_course", Demo.class);
		query.setParameter("id_of_course", id_of_course);
		List<Demo> resultList = (List<Demo>) query.getResultList();
	   
		if(resultList.isEmpty()) {
			query = null;
			resultList = null;
			System.gc();
			return null;
		}
		
		Demo demo = resultList.get(0);
		
		ListOfSubtitlesAbstractFactory listOfSubtitleAbstractFactory = new ListOfSubtitlesAbstractFactory();
		//demo.setQueueOfSubtitleDemo(queueOfSubtitleAbstractFactory.create(id_of_course, 0));
		ListOfSubtitles listOfSubtitle = listOfSubtitleAbstractFactory.create(demo.getId(), 0);
		
		if(withSubtitle) {
			/*
			StringBuffer stringOfSubtitles = new StringBuffer("[");
			for(Subtitle subtitle : queueOfSubtitle.getSubtitles()) {
				if(!stringOfSubtitles.toString().equals("["))stringOfSubtitles.append(", ");
				stringOfSubtitles.append("['"+subtitle.getSrc()+"','"+subtitle.getKind()+"','"+subtitle.getLabel()+"','"
				+subtitle.getSrclang()+"','"+subtitle.getLanguage()+"']");
			}
			stringOfSubtitles.append("]");
			demo.setListOfSubtitleDemo(stringOfSubtitles.toString());
			stringOfSubtitles = null;
			*/
			demo.setListOfSubtitleDemo(listOfSubtitle.getSubtitles());
		}
		
		demo.setSrcVideo(getSrcVideo(demo));
		demo.setSrcPoster(getSrcPoster(demo));
		
		listOfSubtitleAbstractFactory = null;
		query = null;
		resultList = null;
		System.gc();
		
		
		return demo;
	}
	
	private String getSrcVideo(Demo demo) {
		File srcDemo = new File(demo.getBasePath() + "/" + demo.getId() + "/playlist.m3u8");
		if(srcDemo.exists()) {
			srcDemo = null;
			System.gc();
			return demo.getBasePath() + "/" + demo.getId() + "/playlist.m3u8";
		}
		srcDemo = null;
		System.gc();
		
		return null;
	}
	
	private String getSrcPoster(Demo demo) {
		File posterDemoSrc = new File(demo.getBasePath() + "/" + demo.getId() + "/poster.jpg");
		if(posterDemoSrc.exists()) {
			posterDemoSrc = null;
			System.gc();
			return "public/demo/" + demo.getId() + "/poster.jpg";
		} else {
			posterDemoSrc = null;
			System.gc();
			return "/images/noDemoVideo.jpg";
		}
	}
}
