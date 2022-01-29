package com.nastyabagdasarova.servingwebcontent;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.Accessofuseractive;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.UserService;

@Controller
public class AccountController {

	@Autowired
	private UserService userService;
	//@Autowired
	//private VideoplayService videoplayService;
	@Autowired
	private AccessofuserService accessofuserService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
	
	@GetMapping("/account")
	public String account(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//User user = (User)authentication.getPrincipal();
		//int userId = user.getId();
		//System.out.println("id_of_user = " + userId);
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot + "/login";
		}
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		if(user == null) {
			user = null;
			
			
	          if (authentication != null){    
	              new SecurityContextLogoutHandler().logout(request, response, authentication);
	           }
	         SecurityContextHolder.getContext().setAuthentication(null);
	         
	         authentication = null;
			System.gc();
			return "redirect:" + request.getScheme() +"://" + request.getServerName() + ":8080/";
		}
		
		model.addAttribute("user", user);
		
		/*
		//проверка QR-кода
		UserQRCodeFactory userQRCodeFactory = new UserQRCodeFactory();
		UserQRCode userQRCode = userQRCodeFactory.create(user);
		File userQRFile = new File(userQRCode.getSrc());
		if(!userQRFile.exists()) {
			QRCodeGenerator.generateQRCodeImage(userQRCode);
		}
		*/
		
		
		String title = "Account " + FinalComponent.end_of_title;
		model.addAttribute("title", title);
		model.addAttribute("isAuth", true);
		
		//Accessofuseractive backActiveAccess = null;
		
		ArrayList<Accessofuseractive> exoticList = (ArrayList<Accessofuseractive>)accessofuserService
				.getActiveAccessOfUser(user.getId(), 2);
		model.addAttribute("exoticList", exoticList);
		exoticList = null;

		ArrayList<Accessofuseractive> stripList = (ArrayList<Accessofuseractive>)accessofuserService
				.getActiveAccessOfUser(user.getId(), 1);
		model.addAttribute("stripList", stripList);
		stripList = null;
		
		ArrayList<Accessofuseractive> acrobaticsList = (ArrayList<Accessofuseractive>)accessofuserService
				.getActiveAccessOfUser(user.getId(), 3);
		model.addAttribute("acrobaticsList", acrobaticsList);
		acrobaticsList = null;
		
		/*
		int i = 0;
		//String tsDateOfAdd = "";
		Videoplay videoplay;
		File posterSrc = null;
		for(Accessofuseractive access : acrobaticsList) {
			
			//tsDateOfAdd = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(access.getDate_of_activation());
			//access.setDate_must_be_used_to_print(tsDateOfAdd);
			
			access.setDate_of_add_to_print(new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(access.getDate_of_add()));
			
			if(access.getDate_must_be_used() != 0 && (access.getDate_must_be_used() - 10 * 86400) < (int)(System.currentTimeMillis() / 1000L)) {
				access.setProlong(true);
				System.out.println("Можно продлить\n");
			} else {
				access.setProlong(false);
			}

			if(access.getDate_must_be_used() != 0) {
				access.setDate_must_be_used_to_print(new SimpleDateFormat("dd.MM.YYYY")
						.format(new Date((long)(access.getDate_must_be_used() * 1000L))));
			}
			if(access.getDate_of_activation() != 0) {
				access.setDate_of_activation_to_print(new SimpleDateFormat("dd.MM.YYYY")
						.format(new Date((long)(access.getDate_of_activation() * 1000L))));
			}
			
			if(access.getId_of_course() != 0) {
				backActiveAccess = accessofuserService.findBackActiveByIdOfProduct(access.getId_of_course(), "acrobatics");
				access.setBackActiveAccess(backActiveAccess);
			}

			videoplay = videoplayService.findByIdOfContent(access.getId_of_course(), user.getId(), "acrobatics");
			if(videoplay == null) {
				access.setLast_played_number(1);
			} else {
				access.setLast_played_number(videoplay.getNumber());
			}

			posterSrc = new File("uploads/images/acrobatics/" + access.getId_of_course() + ".jpg");
			if(posterSrc.exists()) {
				access.setPosterSrc("uploads/images/acrobatics/" + access.getId_of_course() + ".jpg");
			} else {
				access.setPosterSrc("uploads/images/acrobatics/default.jpg");
			}
			
			acrobaticsList.set(i, access);
			i++;
		}
		*/
		
		
		

		title = null;
		user = null;
		acrobaticsList = null;
		//videoplay = null;
		//posterSrc = null;
		
		System.gc();
		
		
		return "account";
	}
}
