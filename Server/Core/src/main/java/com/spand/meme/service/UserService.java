package com.spand.meme.service;

import com.spand.meme.model.AppUser;

import java.util.List;

public interface UserService {
	
	AppUser findUserById(Long id);

	List<AppUser> findUserByName(String name);

	AppUser findUserByLogin(String login);

	AppUser performUserAuthorization(String login, String password);

	AppUser registerNewUser(AppUser appUser);

	AppUser updateUser(AppUser appUser);

	AppUser deactivateUser(AppUser appUser);

	AppUser activateUser(AppUser appUser);
	
}