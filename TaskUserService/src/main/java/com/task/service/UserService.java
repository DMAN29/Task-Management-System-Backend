package com.task.service;

import java.util.List;

import com.task.model.User;

public interface UserService {

	public User getUserProfile(String jwt);
	public List<User> getAllUsers();
}
