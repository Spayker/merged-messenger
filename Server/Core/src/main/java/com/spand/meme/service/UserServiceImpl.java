package com.spand.meme.service;

import com.spand.meme.model.AppUser;
import com.spand.meme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

    @Autowired
	private UserServiceImpl(UserRepository userRepository){
	    this.userRepository = userRepository;
    }

	public List<AppUser> findByName(String name) {
		return userRepository.findByName(name);
	}

	public AppUser findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public AppUser findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<AppUser> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public AppUser findUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public AppUser saveUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	@Override
	public AppUser deactivateUser(AppUser appUser) {
    	appUser.setActive(false);
		return saveUser(appUser);
	}

	@Override
	public void updateUser(AppUser appUser) {
		AppUser entity = userRepository.findById(appUser.getId());
		if(entity!=null){
			entity.setName(appUser.getName());
			entity.setAddress(appUser.getAddress());
			entity.setPassword(appUser.getPassword());
		}
	}

	@Override
	public boolean isUserExist(String login) {
		AppUser foundAppUser = userRepository.findByLogin(login);
		if(foundAppUser != null){
			return true;
		}
		return false;
	}
}