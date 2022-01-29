package com.nastyabagdasarova.servingwebcontent;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nastyabagdasarova.Component.FinalComponent;
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

@Controller
public class CartController {

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

	@GetMapping("/cart")
	public String cart(Model model, HttpServletRequest request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ((authentication instanceof AnonymousAuthenticationToken)) {
			//return "redirect:" + request.getScheme() +"://" + request.getServerName() + "/login";
			return "redirect:" + request.getScheme() +"://" + FinalComponent.documentRoot +"/login";
		}
		
		String title = "Cart " + FinalComponent.end_of_title;
		model.addAttribute("title", title);
		
		boolean isAuth = true;
		model.addAttribute("isAuth", isAuth);
		
		User user = userService.findByUsername(authenticationFacade.getAuthentication().getName());
		
		List<Cart> cartList = cartService.getCartOfUser(user.getId());
		
		int summ = 0;int i = 0;
		for(Cart cart : cartList) {
			cart.setName("Not found");
			
			if(cart.getId_of_course() != 0) {
				Course contentInfo = courseService.findById(cart.getId_of_course());
				if(contentInfo == null) {
					cartService.deleteById(cart.getId());
					cartList.remove(i);
					continue;
				}
				if(cart.getIs_back() == 0) {
					cart.setName("Course: " + contentInfo.getName());
				}
				if(cart.getIs_back() == 1) {
					Back backInfo = backService.findByIdOfContent(cart.getId_of_course());
					if(backInfo == null) {
						cartService.deleteById(cart.getId());
						cartList.remove(i);
						continue;
					}
					cart.setName("Feedback: " + contentInfo.getName());
				}
			}
			
			summ += cart.getPrice();
			i++;
			cart.setNumber(i);
		}
		model.addAttribute("cartList", cartList);
		model.addAttribute("summ", summ);
		
		
		return "cart";
	}
}
