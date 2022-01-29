package com.nastyabagdasarova.servingwebcontent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nastyabagdasarova.Component.EmailThread;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Model.Role;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.RandomService;
import com.nastyabagdasarova.Service.UserService;

@Controller
public class RegistrationController {

	@Autowired 
	private UserService userService;

	@Autowired 
	private PasswordEncoder passwordEncoder;

	@GetMapping("/registration")
	public String registration(Model model, HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			//return "redirect:" + request.getScheme() +"://" + request.getServerName() + "/lk";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/account";
		}
		

		boolean isGuest = true;
		String title = "Registration | Nastya Bagdasarova - Online Platform";
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("title", title);
		title = null;
		System.gc();
		
		return "registration";
	}
	
	
	@PostMapping("/registration")
	public String addnewUser(User user, Model model, HttpServletRequest request) {
		
		boolean isGuest = true;
		String title = "Registration | Nastya Bagdasarova - Online Platform";
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("title", title);
		title = null;
		
		if(user.getUsername() == "" || user.getFio() == "") {
			model.addAttribute("message", "no_data");
			return "registration";
		}
		
		User userRepeat = userService.findByUsername(user.getUsername());
		if(userRepeat != null) {
			model.addAttribute("message", "username_already_exist");
			return "registration";
		}
		userRepeat = null;
		
		user.setActive(true);
		RandomService randomService = new RandomService();
		String newPassword = randomService.generatedRandomString(6);
		//System.out.println("Новый пароль = " + newPassword);
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setRoles(Collections.singleton(Role.USER));
		

		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		user.setDate_of_add(timestamp);
		
		userService.save(user);
		
		randomService = null;
		timestamp = null;
		
		
		
		//тут нужно отправить пароль на почту пользователя
		
		
		try {
	        EmailThread emailThread = new EmailThread(user.getId(), user.getUsername(), "Thank you for registration!",
					"<center><img src='https://"+FinalComponent.documentRoot+"/images/logo.png' style='height:50px;width:50px;' />"
							+ "</center><p>Login: <b>"+ user.getUsername() +"</b><br /><p>Password: <b>"+ newPassword +"</b></p>", null, null);
			emailThread.start();
			
	        request.login(user.getUsername(), newPassword);
	        

			System.gc();
	        return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/account";
	    } catch (ServletException e) {
			model.addAttribute("message", "unknown_error");
	        return "registration";
	    }
		
	}
	
}
