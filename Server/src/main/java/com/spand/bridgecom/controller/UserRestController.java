package com.spand.bridgecom.controller;

import com.spand.bridgecom.model.User;
import com.spand.bridgecom.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/")
public class UserRestController {

    private static final Logger logger = Logger.getLogger(UserRestController.class);
 
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work

    //-------------------Create a User--------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User " + user.getUsername());
        if (userService.isUserExist(user.getId(),user.getUsername())) {
            logger.info("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        userService.saveUser(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}