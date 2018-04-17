package com.spand.bridgecom.rest.controller;

import com.spand.bridgecom.exception.rest.RestException;
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
        value       = "Creates new user in system",
        response    = UserDetails.class,
        nickname    = "createNewUser"
    )
    public ResponseEntity createNewUser(@RequestBody UserRequest userRequest) {
        AppUser appUserToBeSaved = userRequest.getAppUser();
        String login = appUserToBeSaved.getLogin();
        if (userServiceImpl.isUserExist(login)) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        AppUser savedUser = userServiceImpl.saveUser(appUserToBeSaved);

        if(savedUser == null){
            UserDetails userDetails = USER_MAPPER.userToUserDetails(appUserToBeSaved);
            return new ResponseEntity(userDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            UserDetails userDetails = USER_MAPPER.userToUserDetails(savedUser);
            return new ResponseEntity(userDetails, HttpStatus.CREATED);
        }
    }
}