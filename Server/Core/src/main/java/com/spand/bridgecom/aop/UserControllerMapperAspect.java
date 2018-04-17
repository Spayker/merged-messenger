package com.spand.bridgecom.aop;

import com.spand.bridgecom.model.AppUser;
import com.spand.bridgecom.rest.model.UserDetails;
import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.spand.bridgecom.rest.model.mapper.UserMapper.USER_MAPPER;

@Aspect
@Component
public class UserControllerMapperAspect {

    private UserControllerLoggingAspect userControllerLoggingAspect;

    @Autowired
    private UserControllerMapperAspect(UserControllerLoggingAspect userControllerLoggingAspect){
        this.userControllerLoggingAspect = userControllerLoggingAspect;
    }

    @Pointcut("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)) && args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {}

    @Before("createNewUser(userRequest)")
    public void logIncomeRestUserEvent(UserRequest userRequest) {
        mapIncomeUserRequest(userRequest);
    }

    private void mapIncomeUserRequest(UserRequest userRequest) {
        AppUser appUser = USER_MAPPER.userRequestToUser(userRequest);
        userRequest.setAppUser(appUser);
    }

}
