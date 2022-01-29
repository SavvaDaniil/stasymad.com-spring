package com.nastyabagdasarova.Restservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.Course;
import com.nastyabagdasarova.Model.Back;
import com.nastyabagdasarova.Model.Cart;
import com.nastyabagdasarova.Model.Json;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.CourseService;
import com.nastyabagdasarova.Service.BackService;
import com.nastyabagdasarova.Service.CartService;
import com.nastyabagdasarova.Service.UserService;

@RestController
public class CartrestController {

	@Autowired
	private UserService userService;
	@Autowired
	private CartService cartService;
	@Autowired
	private BackService backService;
	@Autowired
	private CourseService courseService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

	@PostMapping("/cart/add")
	public Json add(
			@RequestParam(defaultValue = "0") int id_of_course,
			@RequestParam(defaultValue = "0") int isBack,
			@RequestParam(defaultValue = "") int operation
		) {
		Json answer = new Json();
		if(id_of_course == 0) {
			answer = new Json("error","no_data");
			return answer;
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			answer = new Json("error","quest");
			return answer;
		}
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		

		//String timestamp = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
	    //        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String timestamp = LocalDateTime.now(ZoneId.of("UTC+06:00"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		
		
		//есть ли в корзине без обратной связи
		if(isBack == 0) {
			Cart checkAlreadyExist = cartService.getContentById(user.getId(), id_of_course, isBack, operation);
			if(checkAlreadyExist == null) {
				
				Course contentInfo = courseService.findById(id_of_course);
				if(contentInfo == null) {
					answer = new Json("error","wrong_id");
					return answer;
				}

				Cart newCart = new Cart();
				newCart.setId_of_user(user.getId());
				newCart.setOperation(operation);
				newCart.setIs_back(0);
				newCart.setKind(contentInfo.getKind());
				newCart.setId_of_course(id_of_course);
				newCart.setDate_of_add(ptsc.parseTimestamp(timestamp));
				newCart.setDays(contentInfo.getDays());
				newCart.setPrice(contentInfo.getPrice());
				cartService.save(newCart);
				
				answer = new Json("success","");
				return answer;
				
			} else {
				answer = new Json("error","already_done");
				return answer;
			}
		}
		
		
		//есть ли в корзине с обратной связью
		if(isBack == 1) {
			Cart checkAlreadyExistProduct = cartService.getContentById(user.getId(), id_of_course, 0, operation);
			Cart checkAlreadyExistBack = cartService.getContentById(user.getId(), id_of_course, 1, operation);
			
			if(!(checkAlreadyExistProduct == null) && !(checkAlreadyExistBack == null)) {
				answer = new Json("error","already_done");
				return answer;
			}
			
			Course contentInfo = courseService.findById(id_of_course);
			if(contentInfo == null) {
				answer = new Json("error","wrong_id");
				return answer;
			}
			
			if(checkAlreadyExistProduct == null) {
				
				Cart newCart = new Cart();
				newCart.setId_of_user(user.getId());
				newCart.setOperation(operation);
				newCart.setIs_back(0);
				newCart.setKind(contentInfo.getKind());
				newCart.setId_of_course(id_of_course);
				newCart.setDate_of_add(ptsc.parseTimestamp(timestamp));
				newCart.setDays(contentInfo.getDays());
				newCart.setPrice(contentInfo.getPrice());
				System.out.println("Проверка");
				cartService.save(newCart);
				
			}
			
			if(checkAlreadyExistBack == null) {
				Back back = backService.findByIdOfContent(id_of_course);
				if(back == null) {
					answer = new Json("error","wrong_id");
					return answer;
				}
				Cart newBackCart = new Cart();
				newBackCart.setId_of_user(user.getId());
				newBackCart.setOperation(operation);
				newBackCart.setIs_back(1);
				newBackCart.setKind(back.getKind());
				newBackCart.setId_of_course(id_of_course);
				newBackCart.setDate_of_add(ptsc.parseTimestamp(timestamp));
				newBackCart.setDays(back.getDays());
				newBackCart.setPrice(back.getPrice());
				
				cartService.save(newBackCart);
			}

		}
		
		/*
		//это только обратная связь
		if(product.equals("back_acrobatics")) {
			Cart checkAlreadyExistBack = cartService.getContentById(user.getId(), id_of_course, 1, operation);

			if(checkAlreadyExistBack == null) {
				
				Course contentInfo = courseService.findById(id_of_course);
				if(contentInfo == null) {
					answer = new Json("error","wrong_id");
					return answer;
				}
				Back back = backService.findByIdOfContent(id_of_course);
				if(back == null) {
					answer = new Json("error","wrong_id");
					return answer;
				}
				Cart newBackCart = new Cart();
				newBackCart.setId_of_user(user.getId());
				newBackCart.setOperation(operation);
				newBackCart.setIs_back(1);
				newBackCart.setKind(back.getKind());
				newBackCart.setId_of_course(id_of_course);
				newBackCart.setDate_of_add(ptsc.parseTimestamp(timestamp));
				newBackCart.setDays(back.getDays());
				newBackCart.setPrice(back.getPrice());
				
				cartService.save(newBackCart);
				
			} else {
				answer = new Json("error","already_done");
				return answer;
			}
		}
		*/
		
		
		
		
		
		

		answer = new Json("success","");
		return answer;
	}

	@PostMapping("/cart/remove")
	public Json remove(
		@RequestParam(defaultValue = "0") int id) {
		Json answer = new Json();
		
		cartService.deleteById(id);
		answer = new Json("success","");
		
		return answer;
	}
}
