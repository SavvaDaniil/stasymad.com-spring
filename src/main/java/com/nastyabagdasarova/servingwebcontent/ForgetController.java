package com.nastyabagdasarova.servingwebcontent;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Component.FinalComponent;

@Controller
public class ForgetController {


	@GetMapping("/forget")
	public String forget(Model model, HttpServletRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			//return "redirect:" + request.getScheme() +"://" + request.getServerName() + "/lk";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/lk";
		}
		
		String title = "Password recovery | Nastya Bagdasarova - Online Platform";
		
		model.addAttribute("isGuest", true);
		model.addAttribute("title", title);
		title = null;
		System.gc();
		
		return "forget";
	}
}
