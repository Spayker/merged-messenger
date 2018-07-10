package com.spand.meme.aop;

import com.spand.meme.model.AppUser;
import com.spand.meme.rest.model.UserRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.spand.meme.rest.model.mapper.UserMapper.USER_MAPPER;

@Aspect
@Component
public class UserControllerMapperAspect {

    private UserControllerLoggingAspect userControllerLoggingAspect;

    @Autowired
    private UserControllerMapperAspect(UserControllerLoggingAspect userControllerLoggingAspect){
        this.userControllerLoggingAspect = userControllerLoggingAspect;
    }

    @Pointcut("(execution(public * com.spand.meme.rest.controller.UserRestController.*(..)) " +
            "&& args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {}

    @Before("createNewUser(userRequest)")
    public void mapIncomeRestUser(UserRequest userRequest) {
        mapIncomeUserRequest(userRequest);
    }

    private void mapIncomeUserRequest(UserRequest userRequest) {
        userControllerLoggingAspect.logDebugMessage("Going to map next request from user: " + userRequest.getLogin());
        AppUser appUser = USER_MAPPER.userRequestToUser(userRequest);
        if(appUser == null){
            userControllerLoggingAspect.logErrorMessage("Request from user: " + userRequest.getLogin() + " was not mapped");
        }
        userRequest.setAppUser(appUser);
    }

}