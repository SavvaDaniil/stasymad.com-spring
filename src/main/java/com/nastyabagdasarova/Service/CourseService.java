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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nastyabagdasarova.AbstractFactory.ListOfSubtitlesAbstractFactory;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.BackService;
import com.nastyabagdasarova.Model.Accessofuseractive;
import com.nastyabagdasarova.Model.Back;
import com.nastyabagdasarova.Model.Course;
import com.nastyabagdasarova.Model.Demo;
import com.nastyabagdasarova.Model.ListOfSubtitles;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.ConnectionCourseToType;
import com.nastyabagdasarova.Repo.CourseRepository;

@Service
public class CourseService {

	@PersistenceContext
	EntityManager entityManager;
	
	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("accessofuser-active");


	@Autowired 
	private BackService backService;
	@Autowired
	private AccessofuserService accessofuserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
	@Autowired
	private UserService userService;
	@Autowired
	private DemoService demoService;
	

	@Autowired 
	private CourseRepository courseRepository;
	
	public List<Course> listAll(){
		return (List<Course>) courseRepository.findAll();
	}
	
	public Course findById(int id) {
		return courseRepository.findById(id);
	}
	
	
	public List<Course> listAllByIdOfTypeOfCourseOrReturnNull(int id_of_type_of_course){
		
		
		entityManager = entityManagerFactory.createEntityManager();
		
		Query<ConnectionCourseToType> queryCoursesOfType = (Query<ConnectionCourseToType>) entityManager.createQuery(
				"SELECT p as ConnectionCourseToType FROM ConnectionCourseToType p "+
				" WHERE p.id_of_type = :id_of_type_of_course"
		   		+ " GROUP BY p.id", ConnectionCourseToType.class);
		queryCoursesOfType.setParameter("id_of_type_of_course", id_of_type_of_course);
		List<ConnectionCourseToType> resultListCoursesOfType = (List<ConnectionCourseToType>) queryCoursesOfType.getResultList();
		queryCoursesOfType = null;
		
		StringBuffer queryIdOfCourses = new StringBuffer("");
		for(ConnectionCourseToType noteCourseOfType : resultListCoursesOfType){
			if(queryIdOfCourses.toString().equals("")) {
				queryIdOfCourses.append("(");
			}
			if(!queryIdOfCourses.toString().equals("") && !queryIdOfCourses.toString().equals("(")) {
				queryIdOfCourses.append(" OR");
			}
			queryIdOfCourses.append(" p.id = " + noteCourseOfType.getId_of_course());
		}
		if(!queryIdOfCourses.toString().equals("")) {
			queryIdOfCourses.append(")");
		}
		resultListCoursesOfType = null;
		if(queryIdOfCourses.toString().equals("")) {
			return null;
		}
		
		
		Query<Course> query = (Query<Course>) entityManager.createQuery("SELECT p as Course FROM Course p "+
			" WHERE "+ queryIdOfCourses
	   		+ " AND status = '1' GROUP BY p.order_in_list ORDER BY p.order_in_list DESC", Course.class);
		
	   List<Course> resultList = (List<Course>) query.getResultList();
	   
	   /////////////////////////////////////////////////
	   


		List<Accessofuseractive> listActiveCourse;
		List<Integer> listAlreadyBuyedCourseId = new ArrayList<Integer>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
			listActiveCourse = accessofuserService.getActiveAccessOfUser(user.getId(), id_of_type_of_course);
			for(Accessofuseractive activeCourse : listActiveCourse){
				listAlreadyBuyedCourseId.add(activeCourse.getId_of_course());
			}
			
			user = null;
		}
		listActiveCourse = null;

		
		List<Back> backList = backService.listAll();
		File posterOriginalSrc = null;
		File posterDemoSrc = null;
		File srcDemo = null;
		
		//QueueOfSubtitleAbstractFactory queueOfSubtitleAbstractFactory = new QueueOfSubtitleAbstractFactory();
		
		int i = 0;
		for(Course courseInfo : resultList){
			if(listAlreadyBuyedCourseId.contains(courseInfo.getId())) {
				courseInfo.setAlreadyBuyed(1);
			}

			for(Back backInfo : backList){
				if(backInfo.getId_of_course() == courseInfo.getId()) {
					courseInfo.setBack(backInfo);
				}
			}
			
			posterOriginalSrc = new File("public/course/" + courseInfo.getId() + "/original.jpg");
			if(posterOriginalSrc.exists()) {
				courseInfo.setPosterOriginal("/public/course/" + courseInfo.getId() + "/original.jpg");
			} else {
				courseInfo.setPosterOriginal("/images/noOriginalVideo.jpg");
			}
			//System.out.println("Path to original = " + posterOriginalSrc.getAbsolutePath());
			
			
			if(courseInfo.getOriginal_inst1_youtube2() == 1) {
				courseInfo.setSrcOriginal(courseInfo.getInstagram());
			} else if(courseInfo.getOriginal_inst1_youtube2() == 2) {
				courseInfo.setSrcOriginal(courseInfo.getYoutube());
			}
			
			//выясняем наличие демо
			/*
			srcDemo = new File("public/demo/" + courseInfo.getId() + "/playlist.m3u8");
			
			if(srcDemo.exists()) {
				courseInfo.setSrcDemo("/public/demo/" + courseInfo.getId() + "/playlist.m3u8");
				
				posterDemoSrc = new File("public/demo/" + courseInfo.getId() + "/poster.jpg");
				if(posterDemoSrc.exists()) {
					courseInfo.setPosterDemo("public/demo/" + courseInfo.getId() + "/poster.jpg");
				} else {
					courseInfo.setPosterDemo("/images/noDemoVideo.jpg");
				}
				
				
				courseInfo.setQueueOfSubtitleDemo(queueOfSubtitleAbstractFactory.create(courseInfo.getId(), 0));
				
				
			} else {
				courseInfo.setPosterDemo("/images/noDemoVideo.jpg");
			}
			*/
			Demo demo = demoService.findByIdOfCourse(courseInfo.getId(), false);
			if(demo != null) {
				courseInfo.setDemo(demo);
				courseInfo.setPosterDemo(demo.getSrcPoster());
			} else {
				courseInfo.setPosterDemo("/images/noDemoVideo.jpg");
			}
			demo = null;
			
			
			resultList.set(i, courseInfo);
			
			i++;
		}
	   
		authentication = null;
		backList = null;
		posterOriginalSrc = null;
		posterDemoSrc = null;
		//queueOfSubtitleAbstractFactory = null;
		
	   /////////////////////////////////////////////////
	   
	   queryIdOfCourses = null;
	   query = null;
	   System.gc();
	   
	   
		
		return resultList;
	}
	
	
	public Course findByIdWithDemoOrReturnNull(int id) {
		Course course = findById(id);
		if(course == null) {
			return null;
		}
		
		
		
		
		return null;
	}
}
