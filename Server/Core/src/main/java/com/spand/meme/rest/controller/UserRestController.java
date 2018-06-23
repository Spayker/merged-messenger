package com.spand.meme.rest.controller;

import com.spand.meme.model.AppUser;
import com.spand.meme.rest.model.UserDetails;
import com.spand.meme.rest.model.UserRequest;
import com.spand.meme.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.spand.meme.rest.model.mapper.UserMapper.USER_MAPPER;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class UserRestController {

    private UserService userServiceImpl;

    @Autowired
    public UserRestController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation(
            value = "Creates new user in system",
            response = UserDetails.class,
            nickname = "createNewUser"
    )
    public ResponseEntity registerNewUser(@Valid @RequestBody UserRequest userRequest) {
        AppUser appUserToBeSaved = userRequest.getAppUser();
        String login = appUserToBeSaved.getLogin();
        if (userServiceImpl.findUserByLogin(login) == null) {
            return new ResponseEntity(CONFLICT);
        }
        AppUser savedUser = userServiceImpl.registerNewUser(appUserToBeSaved);

        if (savedUser == null) {
            UserDetails userDetails = USER_MAPPER.userToUserDetails(appUserToBeSaved);
            return new ResponseEntity(userDetails, INTERNAL_SERVER_ERROR);
        } else {
            UserDetails userDetails = USER_MAPPER.userToUserDetails(savedUser);
            return new ResponseEntity(userDetails, CREATED);
        }
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ApiOperation(
            value = "Activate user in system",
            response = UserDetails.class,
            nickname = "activateNewUser"
    )
    public ResponseEntity activateUser(@RequestBody UserRequest userRequest) {
        String login = userRequest.getLogin();
        AppUser foundUser = userServiceImpl.findUserByLogin(login);
        if (foundUser == null) {
            return new ResponseEntity(NOT_FOUND);
        } else {
            AppUser savedUser = userServiceImpl.activateUser(foundUser);
            UserDetails userDetails = USER_MAPPER.userToUserDetails(savedUser);
            return new ResponseEntity(userDetails, OK);
        }
    }
}