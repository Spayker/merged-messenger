package com.spand.bridgecom.service;

import com.spand.bridgecom.model.AppUser;

import java.util.List;


public interface UserService {
	
	AppUser findUserById(Long id);

	List<AppUser> findUserByName(String name);

	AppUser findUserByLogin(String login);

	AppUser saveUser(AppUser appUser);

	AppUser deactivateUser(AppUser appUser);
	
	void updateUser(AppUser appUser);

	boolean isUserExist(String username);
	
}