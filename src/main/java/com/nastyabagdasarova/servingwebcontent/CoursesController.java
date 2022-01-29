package com.nastyabagdasarova.servingwebcontent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Model.Course;
import com.nastyabagdasarova.Service.CourseService;

@Controller
public class CoursesController {

	
	@Autowired 
	private CourseService courseService;
	/*
	@Autowired 
	private BackService backService;
	@Autowired
	private AccessofuserService accessofuserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
	@Autowired
	private UserService userService;
	*/
	

	@GetMapping("/strip")
	public String Index(Model model) {
		
		boolean isGuest = true;
		boolean isAuth = false;
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isAuth = true;
			isGuest = false;
		}
		/*
		List<Accessofuseractive> listActiveCourse;
		List<Integer> listAlreadyBuyedCourseId = new ArrayList<Integer>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isAuth = true;
			isGuest = false;
			
			User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
			listActiveCourse = accessofuserService.getActiveAccessOfUser(user.getId());
			for(Accessofuseractive activeCourse : listActiveCourse){
				listAlreadyBuyedCourseId.add(activeCourse.getId_of_course());
			}
			
			user = null;
		}
		listActiveCourse = null;
		
		
		List<Course> courseList = courseService.listAllByIdOfTypeOfCourseOrReturnNull(2);
		List<Back> backList = backService.listAll();
		File posterOriginalSrc = null;
		File posterDemoSrc = null;
		File srcDemo = null;
		int i = 0;
		for(Course courseInfo : courseList){
			if(listAlreadyBuyedCourseId.contains(courseInfo.getId())) {
				courseInfo.setAlreadyBuyed(1);
			}

			for(Back backInfo : backList){
				if(backInfo.getId_of_course() == courseInfo.getId()) {
					courseInfo.setBack(backInfo);
				}
			}
			
			posterOriginalSrc = new File("public/course_poster/" + courseInfo.getId() + "/original.jpg");
			if(posterOriginalSrc.exists()) {
				courseInfo.setPosterOriginal("/public/course_poster/" + courseInfo.getId() + "/original.jpg");
			} else {
				courseInfo.setPosterOriginal("/images/noOriginalVideo.jpg");
			}
			
			
			if(courseInfo.getOriginal_inst1_youtube2() == 1) {
				courseInfo.setSrcOriginal(courseInfo.getInstagram());
			} else if(courseInfo.getOriginal_inst1_youtube2() == 2) {
				courseInfo.setSrcOriginal(courseInfo.getYoutube());
			}
			
			//выясняем наличие демо
			srcDemo = new File("public/demo/" + courseInfo.getId() + "/playlist.m3u8");
			
			if(srcDemo.exists()) {
				courseInfo.setSrcDemo("/public/demo/" + courseInfo.getId() + "/playlist.m3u8");
				
				posterDemoSrc = new File("public/demo/" + courseInfo.getId() + "/poster.jpg");
				if(posterDemoSrc.exists()) {
					courseInfo.setPosterDemo("public/demo/" + courseInfo.getId() + "/poster.jpg");
				} else {
					courseInfo.setPosterDemo("/images/noDemoVideo.jpg");
				}
				
				
			} else {
				courseInfo.setPosterDemo("/images/noDemoVideo.jpg");
			}
			
			
			courseList.set(i, courseInfo);
			
			i++;
		}
		*/
		List<Course> courseList = courseService.listAllByIdOfTypeOfCourseOrReturnNull(1);
		
		String header = "Strip choreography";
		String title = header + "| Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("courseList", courseList);
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("menu", 4);
		model.addAttribute("header", header);
		model.addAttribute("title", title);
		
		courseList = null;
		header = null;
		title = null;
		/*
		authentication = null;
		backList = null;
		posterOriginalSrc = null;
		posterDemoSrc = null;
		*/
		
		//courseService = null;
		

		System.gc();
		
		
		return "courses";
	}
	

	@GetMapping("/exotic")
	public String Exotic(Model model) {
		
		boolean isGuest = true;
		boolean isAuth = false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isAuth = true;
			isGuest = false;
		}
		List<Course> courseList = courseService.listAllByIdOfTypeOfCourseOrReturnNull(2);
		
		String header = "Frame Up Exotic choreography";
		String title = header + "| Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("courseList", courseList);
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("menu", 2);
		model.addAttribute("header", header);
		model.addAttribute("title", title);
		
		courseList = null;
		header = null;
		title = null;
		
		System.gc();
		
		
		return "courses";
	}
	

	@GetMapping("/acrobatics")
	public String Acrobatics(Model model) {
		
		boolean isGuest = true;
		boolean isAuth = false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isAuth = true;
			isGuest = false;
		}
		List<Course> courseList = courseService.listAllByIdOfTypeOfCourseOrReturnNull(3);
		
		String header = "Acrobatics";
		String title = header + "| Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("courseList", courseList);
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("menu", 3);
		model.addAttribute("header", header);
		model.addAttribute("title", title);
		
		courseList = null;
		header = null;
		title = null;
		
		System.gc();
		
		
		return "courses";
	}
}
