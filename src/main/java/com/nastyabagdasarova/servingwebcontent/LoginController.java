package com.nastyabagdasarova.servingwebcontent;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Component.FinalComponent;

@Controller
public class LoginController {

	//@Autowired 
	//private PasswordEncoder passwordEncoder;

	
	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			//return "redirect:" + request.getScheme() +"://" + request.getServerName() + "/lk";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/lk";
		}
		
		boolean isGuest = true;
		String title = "Login | Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("title", title);
		
		title = null;
		System.gc();
		
		//System.out.println(passwordEncoder.encode("p"));
		
		return "login";
	}
	
	
}
