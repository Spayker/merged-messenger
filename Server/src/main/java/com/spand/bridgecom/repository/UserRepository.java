package com.spand.bridgecom.repository;

import com.spand.bridgecom.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Spayker on 2/4/2018.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findById(Long id);

    List<User> findByName(String name);

}
