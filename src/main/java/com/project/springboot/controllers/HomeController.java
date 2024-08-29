package com.project.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/home")
	public String home() {
		LOG.info("At Home");
		return "At Home";
	}
	
}
