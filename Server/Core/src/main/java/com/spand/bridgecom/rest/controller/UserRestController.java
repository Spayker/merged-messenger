package com.spand.bridgecom.rest.controller;

import com.spand.bridgecom.model.AppUser;
import com.spand.bridgecom.rest.model.UserDetails;
import com.spand.bridgecom.rest.model.UserRequest;
import com.spand.bridgecom.rest.model.mapper.UserMapper;
import com.spand.bridgecom.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.spand.bridgecom.rest.model.mapper.UserMapper.USER_MAPPER;

@RestController
public class UserRestController {
 
    @Autowired
    UserService userServiceImpl;

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

}