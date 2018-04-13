package com.spand.bridgecom.rest.controller;

import com.spand.bridgecom.model.User;
import com.spand.bridgecom.rest.model.UserDetails;
import com.spand.bridgecom.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("/users")
public class UserRestController {
 
    @Autowired
    UserService userServiceImpl;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(
            value = "Creates new user in system",
            response = UserDetails.class,
            nickname = "createNewUser"
    )
    public ResponseEntity<UserDetails> createNewUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        //logger.info("Creating User " + user.getUsername());
        if (userServiceImpl.isUserExist(user.getId(),user.getLogin())) {
            //logger.info("A User with name " + user.getUsername() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userServiceImpl.saveUser(user);
        ResponseEntity<UserDetails> responseEntity = new ResponseEntity(new UserDetails(), HttpStatus.CREATED);
        return responseEntity;
    }


}