package com.spand.bridgecom.repository;

import com.spand.bridgecom.model.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Spayker on 2/4/2018.
 */
public interface UserRepository extends CrudRepository<AppUser, Long> {

    AppUser findById(Long id);

    List<AppUser> findByName(String name);

    AppUser findByLogin(String login);

}
