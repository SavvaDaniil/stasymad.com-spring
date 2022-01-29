package com.nastyabagdasarova.servingwebcontent;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.Accessofuser;
import com.nastyabagdasarova.Model.Accessofuseractive;
import com.nastyabagdasarova.Repo.UserRepository;
import com.nastyabagdasarova.Service.AccessofuserService;
import com.nastyabagdasarova.Service.EmailService;

@RestController
public class ApiController {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private AccessofuserService accessofuserService;
	
	
	
	@GetMapping("/api/test")
	public String test() {
		
		/*
		User n = new User();
	    n.setUsername("savva.d@mail.ru");
	    userRepository.save(n);
	    */
		
		
		/*
		Accessofuser n = new Accessofuser();
		String timestamp = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
	            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		n.setDate_of_add(parseTimestamp(timestamp));
		accessofuserService.save(n);
		*/
		
		/*
		//List<Accessofuseractive> list = accessofuserService.getActiveAccessOfUser(1, 1, "acrobatics");
		List<Object> list = accessofuserService.getActiveAccessOfUser(1, 1, "acrobatics");
		
		for (int i = 0; i < list.size(); i++) {
           System.out.println(list.get(i) + "\n");
            
            Object[] o = (Object[]) list.get(i);
            //Accessofuser a = (Accessofuser)list.get(i).getAccessofuser();
            Accessofuser a = (Accessofuser)o[0];
            
            System.out.println(a.getId_of_user() + "\n");
            

            //System.out.println("Пробуем дальше вытащить " + list.get(i).getName_of_acrobatics() + "\n");
          }
		*/
		
		/*
		EmailService es = new EmailService();
		try {
			es.sendSimpleMessage("savva.d@mail.ru", "Проверка темы", "<p>Проверка html</p>");
		} catch(MessagingException mex) {
			mex.getMessage();
		} catch(IOException ioex) {
			ioex.getMessage();
		}
		*/
		
		return "success";
	}
	
	/*
	private java.util.Date parseTimestamp(String timestamp) {
	    try {
	        return DATE_TIME_FORMAT.parse(timestamp);
	    } catch (ParseException e) {
	        throw new IllegalArgumentException(e);
	    }
	}
	*/
}
