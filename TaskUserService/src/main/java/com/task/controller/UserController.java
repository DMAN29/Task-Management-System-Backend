package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.User;
import com.task.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
		User user = userService.getUserProfile(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@GetMapping("")
	public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String jwt){
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
}
