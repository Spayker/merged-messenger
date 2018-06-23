package com.spand.meme.repository;

import com.spand.meme.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Spayker on 2/4/2018.
 */
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findById(Long id);

    List<AppUser> findByName(String name);

    List<AppUser> findByPassword(String password);

    List<AppUser> findByAddress(String address);

    List<AppUser> findByIsActive(Boolean active);

    AppUser findByLogin(String login);

    AppUser findByLoginAndPassword(String login, String password);

}