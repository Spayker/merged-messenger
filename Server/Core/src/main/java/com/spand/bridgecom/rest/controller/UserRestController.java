package com.spand.bridgecom.rest.controller;

import com.spand.bridgecom.model.AppUser;
import com.spand.bridgecom.rest.model.UserDetails;
import com.spand.bridgecom.rest.model.UserRequest;
import com.spand.bridgecom.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.spand.bridgecom.rest.model.mapper.UserMapper.USER_MAPPER;

@RestController
public class UserRestController {

    private UserService userServiceImpl;

    @Autowired
    public UserRestController(UserService userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation(
        value = "Creates new user in system",
        response = UserDetails.class,
        nickname = "createNewUser"
    )
    public ResponseEntity<UserDetails> createNewUser(@RequestBody UserRequest userRequest) {
        AppUser appUserToBeSaved = USER_MAPPER.userRequestToUser(userRequest);

        if (userServiceImpl.isUserExist(appUserToBeSaved.getLogin())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        AppUser savedAppUser = userServiceImpl.saveUser(appUserToBeSaved);
        UserDetails userDetails = USER_MAPPER.userToUserDetails(savedAppUser);
        ResponseEntity<UserDetails> responseEntity = new ResponseEntity(userDetails, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    @ApiOperation(
        value = "Login",
        response = UserDetails.class,
        nickname = "loginUser"
    )
    public ResponseEntity<UserDetails> loginUser(@RequestParam UserRequest userRequest) {
        ResponseEntity<UserDetails> responseEntity = new ResponseEntity(new Object(), HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/users/", method = RequestMethod.DELETE)
    @ApiOperation(
        value = "Deactivate",
        response = UserDetails.class,
        nickname = "deactivateUser"
    )
    public ResponseEntity<UserDetails> deactivateUser(@RequestParam UserRequest userRequest) {
        ResponseEntity<UserDetails> responseEntity = new ResponseEntity(new Object(), HttpStatus.OK);
        return responseEntity;
    }



}