package com.spand.bridgecom.service;

import com.spand.bridgecom.model.AppUser;
import com.spand.bridgecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	public List<AppUser> findByName(String name) {
		return userRepository.findByName(name);
	}

	public AppUser findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public AppUser findUserById(Long id) {
		return userRepository.findById(id);
	}

	public List<AppUser> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	public AppUser saveUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	public void updateUser(AppUser appUser) {
		AppUser entity = userRepository.findById(appUser.getId());
		if(entity!=null){
			entity.setName(appUser.getName());
			entity.setAddress(appUser.getAddress());
			entity.setPassword(appUser.getPassword());
		}
	}

	public boolean isUserExist(String login) {
		AppUser foundAppUser = userRepository.findByLogin(login);
		if(foundAppUser !=null){
			return true;
		}
		return false;
	}
}