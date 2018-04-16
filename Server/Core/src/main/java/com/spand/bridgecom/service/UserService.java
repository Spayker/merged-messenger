package com.spand.bridgecom.service;

import com.spand.bridgecom.model.User;

import java.util.List;


public interface UserService {
	
	User findUserById(Long id);

	List<User> findUserByName(String name);

	User saveUser(User user);
	
	void updateUser(User user);

	boolean isUserExist(String username);
	
}