package com.nastyabagdasarova.Restservice;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Component.EmailThread;
import com.nastyabagdasarova.Component.FinalComponent;
import com.nastyabagdasarova.Interface.IAuthenticationFacade;
import com.nastyabagdasarova.Model.JsonForget;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Service.RandomService;
import com.nastyabagdasarova.Service.ReLoginService;
import com.nastyabagdasarova.Service.UserService;

@RestController
public class ForgetRestController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private IAuthenticationFacade authenticationFacade;

	@Autowired 
	private PasswordEncoder passwordEncoder;

    
	@PostMapping("/rest/forget")
	@ResponseBody
	public JsonForget forget(
			@RequestParam(defaultValue = "") String username,
			@RequestParam(defaultValue = "") String action,
			@RequestParam(defaultValue = "") String code,
			@RequestParam(defaultValue = "") String hash
			) {
		JsonForget answer = new JsonForget();
		
		
		if(!action.equals("0") && !action.equals("1")) {
			answer = new JsonForget("error","no_action","","");
			
			username = null;
			action = null;
			code = null;
			hash = null;
			System.gc();
			
			return answer;
		}
		if(username.equals("")) {
			answer = new JsonForget("error","no_data","","");
			
			username = null;
			action = null;
			code = null;
			hash = null;
			System.gc();
			
			return answer;
		}
		User user = userService.findByUsername(username);
		if(user == null) {
			answer = new JsonForget("error","no_user","","");
			
			username = null;
			action = null;
			code = null;
			hash = null;
			user = null;
			System.gc();
			
			return answer;
		}
		RandomService randomService = new RandomService();
		
		if(action.equals("0")) {
			
			//проверяем сколько попыток
			int forgetCount = user.getForgetCount();
			if(user.getForgetLast() > (System.currentTimeMillis() / 1000L - 60*20) && forgetCount > 2) {
				answer = new JsonForget("error","please_wait_20","","");
				
				username = null;
				action = null;
				code = null;
				hash = null;
				user = null;
				randomService = null;
				System.gc();
				
				return answer;
			}
			

			
			String forgetCode = randomService.generatedRandomString(6);
			String forgetHash = randomService.generatedRandomString(32);
			
			user.setForgetCode(forgetCode);
			user.setForgetHash(forgetHash);
			user.setForgetTry(0);
			user.setForgetCount(forgetCount + 1);
			user.setForgetLast((int)(System.currentTimeMillis() / 1000L));
			userService.save(user);
			
			
			
			
			EmailThread emailThread = new EmailThread(user.getId(), user.getUsername(), "Confirmation code to change password",
					"<center><img src='"+FinalComponent.documentRoot+"/images/logo.png' style='height:50px;width:50px;' />"
							+ "</center><p><b>"+ forgetCode +"</b></p>", null, null);
			emailThread.start();
			
			answer = new JsonForget("success","", forgetCode, forgetHash);
			return answer;
			
			
		} else if(action.equals("1")) {
			if(code.equals("") || hash.equals("")) {
				answer = new JsonForget("error","no_data","","");
				
				username = null;
				action = null;
				code = null;
				hash = null;
				user = null;
				randomService = null;
				System.gc();
				
				return answer;
			}
			if(user.getForgetTry() > 2) {
				answer = new JsonForget("error","limit_try","","");
				
				username = null;
				action = null;
				code = null;
				hash = null;
				user = null;
				randomService = null;
				System.gc();
				
				return answer;
			}
			
			if(hash.equals(user.getForgetHash())) {
				if(code.equals(user.getForgetCode())) {
					user.setForgetCode(null);
					user.setForgetTry(0);
					user.setForgetHash(null);
					user.setForgetCount(0);
					user.setForgetLast((int)(System.currentTimeMillis() / 1000L));
					
					String newPassword = randomService.generatedRandomString(6);
					user.setPassword(passwordEncoder.encode(newPassword));
					
					
					userService.save(user);

					//отправка письма
					EmailThread emailThread = new EmailThread(user.getId(), user.getUsername(),
						"New automatically generated password", 
						"<center><img src='"+FinalComponent.documentRoot+"/images/logo.png' style='height:50px;width:50px;' />"
						+ "</center>\r\n" + 
						"<p>Your new site access <a href='"+FinalComponent.documentRoot+"'>"+FinalComponent.documentRoot+"</a><br /></p>" + 
						"<p>Login: <b>"+user.getUsername()+"</b></p>" + 
						"<p>New password: <b>"
						+ newPassword +"</b></p>", null, null);
					emailThread.start();
					
					
					ReLoginService reLogin = new ReLoginService();
					if(reLogin.init(username, newPassword)) {
						answer = new JsonForget("success","","","");
						
						username = null;
						action = null;
						code = null;
						hash = null;
						user = null;
						randomService = null;
						newPassword = null;
						reLogin = null;
						System.gc();
						
						return answer;
					} else {
						answer = new JsonForget("error","unknown_error","","");
						
						username = null;
						action = null;
						code = null;
						hash = null;
						user = null;
						randomService = null;
						newPassword = null;
						reLogin = null;
						System.gc();
						
						return answer;
					}
					
				} else {
					int forgetTry = user.getForgetTry();
					user.setForgetTry(forgetTry + 1);
					userService.save(user);
					
					switch(forgetTry) {
						case 0:
							answer = new JsonForget("error","wrong_code_2","","");
							return answer;
						case 1:
							answer = new JsonForget("error","wrong_code_1","","");
							return answer;
						default:
							answer = new JsonForget("error","limit_try","","");
							return answer;
					}
				}
			} else {
				answer = new JsonForget("error","error_hash","","");
				return answer;
			}
			
		}
		
		
		answer = new JsonForget("error","error_wrong_action","","");
		
		username = null;
		action = null;
		code = null;
		hash = null;
		user = null;
		randomService = null;
		System.gc();
		
		return answer;
	}
			
			

}
