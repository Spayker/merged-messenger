package com.spand.bridgecom.service;

import com.spand.bridgecom.model.User;
import com.spand.bridgecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	public User findByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public User findUserById(Long id) {
		return userRepository.findById(id);
	}

	public List<User> findUserByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateUser(User user) {
		User entity = userRepository.findById(user.getId());
		if(entity!=null){
			entity.setName(user.getName());
			entity.setAddress(user.getAddress());
			entity.setEmail(user.getEmail());
		}
	}

	public boolean isUserExist(Long id, String username) {
		return false;
	}
}