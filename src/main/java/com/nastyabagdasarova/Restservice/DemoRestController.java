package com.nastyabagdasarova.Restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nastyabagdasarova.Model.Json;
import com.nastyabagdasarova.Model.Demo;
import com.nastyabagdasarova.Service.DemoService;

@RestController
public class DemoRestController {

	@Autowired 
	private DemoService demoService;
	
	@PostMapping("/rest/course/demo/get")
	public Json GetDemo(@RequestParam(defaultValue = "0") int id_of_course) {

		if(id_of_course == 0) {
			return new Json("error","no_data");
		}
		
		Demo demo = demoService.findByIdOfCourse(id_of_course, true);
		if(demo == null) {
			return new Json("error","not_found");
		}
		
		return new Json("success","", demo, 0);
	}
}
