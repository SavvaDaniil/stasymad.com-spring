package com.nastyabagdasarova.servingwebcontent;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
	
	@GetMapping("/")
	public String index(Model model) {
		
		boolean isGuest = true;
		boolean isAuth = false;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    //String currentUserName = authentication.getName();
		    //return currentUserName;
			isAuth = true;
			isGuest = false;
		}
		
		String[] section2Array = {
		    "<p><b>Comfortable viewing and study </b> of material <b> at any time </b>, with the ability to practice each moves as many times as you need</p>",
		    "<p><b>Opportunity for feedback.</b><br />I will advise, answer your questions and provide feedback!</p>",
		    "<p>For 1 online lesson, I will give material, the analysis of which in an offline lesson takes 3 lessons</p>"
		};
		
		
		int menu = 1;
		String title = "Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("section2Array", section2Array);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("isGuest", isGuest);
		model.addAttribute("menu", menu);
		model.addAttribute("title", title);
		
		authentication = null;
		section2Array = null;
		title = null;
		
		System.gc();
		
		return "index";
	}
}
