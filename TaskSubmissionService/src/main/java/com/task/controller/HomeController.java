package com.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/submissions")
	public ResponseEntity<String> homeController(){
		return new ResponseEntity<String>("Welcome to Submission Service",HttpStatus.OK);
	}
}
