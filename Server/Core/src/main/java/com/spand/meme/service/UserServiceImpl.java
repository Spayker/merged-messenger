package com.spand.meme.service;

import com.spand.meme.exception.MeMeException;
import com.spand.meme.model.AppUser;
import com.spand.meme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	public UserServiceImpl() {}

	@Autowired
	private UserRepository userRepository;

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public AppUser findUserById(Long id) {
		return Optional.ofNullable((userRepository.findById(id)))
				.orElseThrow(() -> new MeMeException ("The user with id: " + id + " has not found"));
	}

	@Override
	public List<AppUser> findUserByName(String name) {
		List<AppUser> foundAppUsers = Optional.ofNullable(userRepository.findByName(name))
				.orElseThrow(NullPointerException::new);

		if(foundAppUsers.isEmpty()){
			throw new MeMeException ("The user with name: " + name + " has not found");
		}

		return foundAppUsers;
	}

	@Override
	public AppUser findUserByLogin(String login) {
		return Optional.ofNullable(userRepository.findByLogin(login))
				.orElseThrow(() -> new MeMeException ("The user with login: " + login + " has not found"));
	}

	@Override
	public AppUser performUserAuthorization(String login, String password) {
		AppUser foundUser = Optional.ofNullable(userRepository.findByLoginAndPassword(login, password))
				.orElseThrow(() -> new MeMeException("Wrong credentials for login: " + login + " . Login/Password is incorrect"));
		if(foundUser.getIsActive()){
			return foundUser;
		}
		throw new MeMeException("User with login: " + login + " is not active anymore");
	}

	@Override
	public AppUser registerNewUser(AppUser appUser) {
    	String login = appUser.getLogin();
		AppUser foundUser = userRepository.findByLogin(login);
    	if(foundUser == null){
			String hash = encoder.encode(appUser.getPassword());
    		appUser.setPassword(hash);
			AppUser savedUser = userRepository.save(appUser);
			if(savedUser == null){
				throw new MeMeException("user with login: " + login + " was not created");
			}
			return savedUser;
		}
		throw new MeMeException("user with login: " + login + " already exists");
	}

	@Override
	public AppUser deactivateUser(AppUser appUser) {
		String login = appUser.getLogin();
		AppUser foundUser = Optional.ofNullable((userRepository.findByLogin(login)))
				.orElseThrow(() -> new MeMeException ("The user with login: " + login + " has not found"));
		if(foundUser.getIsActive()){
			foundUser.setIsActive(false);
			AppUser updatedUser = userRepository.save(foundUser);
			return updatedUser;
		}
		throw new MeMeException("User with login: " + login + " is already not active");
	}

	@Override
	public AppUser activateUser(AppUser appUser) {
		String login = appUser.getLogin();
		AppUser foundUser = Optional.ofNullable((userRepository.findByLogin(login)))
				.orElseThrow(() -> new MeMeException ("The user with login: " + login + " has not found"));
		if(!foundUser.getIsActive()){
			foundUser.setIsActive(true);
			AppUser updatedUser = userRepository.save(foundUser);
			return updatedUser;
		}
		throw new MeMeException("User with login: " + login + " is already activated");
	}

	@Override
	public AppUser updateUser(AppUser appUser) {
		if(appUser.getId() == null){
			throw new MeMeException("User with login: " + appUser.getLogin() + " has id with value NULL");
		}
		return userRepository.save(appUser);
	}

}