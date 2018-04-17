package com.spand.bridgecom.aop.rest;

import com.spand.bridgecom.model.AppUser;
import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserControllerMapperAspect {

    private UserControllerLoggingAspect userControllerLoggingAspect;

    @Autowired
    private UserControllerMapperAspect(UserControllerLoggingAspect userControllerLoggingAspect){
        this.userControllerLoggingAspect = userControllerLoggingAspect;
    }

    @Pointcut("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)) && args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {

    }

    @Before("createNewUser(userRequest)")
    public void logIncomeRestUserEvent(UserRequest userRequest) {
        mapIncomeUserRequest(userRequest);
    }

    @Around("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)))")
    public Object employeeAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }

    private void mapIncomeUserRequest(UserRequest userRequest) {

    }

    private void mapOutgoingUser(AppUser appUser) {

    }

}
