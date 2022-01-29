package com.nastyabagdasarova.servingwebcontent;


import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ContactsController {
	
	@GetMapping("/contacts")
	public String Index(Model model) throws SocketException, UnknownHostException {

		
		boolean isGuest = true;
		boolean isAuth = false;
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			isAuth = true;
			isGuest = false;
		}

		String header = "Contacts";
		String title = header + " | Nastya Bagdasarova - Online Platform";

		model.addAttribute("isGuest", isGuest);
		model.addAttribute("isAuth", isAuth);
		model.addAttribute("header", header);
		model.addAttribute("title", title);
		
		
		authentication = null;
		header = null;
		title = null;
		System.gc();
		
		
		return "contacts";
	}
}
