package com.spand.bridgecom.service;

import com.spand.bridgecom.model.User;

import java.util.List;


public interface UserService {
	
	User findUserById(Long id);

	List<User> findUserByName(String name);

	void saveUser(User user);
	
	void updateUser(User user);

	boolean isUserExist(Long id, String username);
	
}