package com.nastyabagdasarova.servingwebcontent;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nastyabagdasarova.Component.MD5;
import com.nastyabagdasarova.Model.Course;
import com.nastyabagdasarova.Model.Back;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.CourseService;
import com.nastyabagdasarova.Service.BackService;
import com.nastyabagdasarova.Service.UserService;

@Controller
public class ChatAdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private BackService backService;
	@Autowired
	private CourseService courseService;

	@GetMapping("/chat/admin")
	public String admin(
		@RequestParam(defaultValue = "0") int id_of_back,
		@RequestParam(defaultValue = "0") int id_of_user,
		@RequestParam(defaultValue = "") String hash, Model model
		) throws NoSuchAlgorithmException {
		if(id_of_back == 0 || id_of_user == 0) {
			return null;
		}
		
		if(!hash.equals(MD5.md5(MD5.md5(MD5.md5(id_of_back +"fdgfhKUJHFDUFHsi74t3t4gsdrsdggrd"))))) {
			
		}
		model.addAttribute("hash", hash);
		
		Back back = backService.findById(id_of_back);
		if(back == null) {
			return null;
		}
		model.addAttribute("back", back);
		
		String subject_product_info = "";
		if(back.getId_of_course() != 0) {
			Course content = courseService.findById(back.getId_of_course());
			if(content != null) {
				subject_product_info = "Контент по акробатике (id" +back.getId_of_course() + ") - " + content.getName();
			}
		} else if(back.getId_of_course() != 0) {

		}
		model.addAttribute("subject_product_info", subject_product_info);
		
		
		User user = userService.findById(id_of_user);
		if(user == null) {
			return null;
		}
		model.addAttribute("user", user);
		
		
		boolean isGuest = true;
		String title = "Обратная связь | Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("title", title);
		
		title = null;
		subject_product_info = null;
		user = null;
		back = null;
		System.gc();
		
		
		return "chatadmin";
	}
}
