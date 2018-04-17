package com.spand.bridgecom.aop;

import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserControllerLoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(UserControllerLoggingAspect.class);

    @Pointcut("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)) && args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {}

    @Before("createNewUser(userRequest)")
    private void logIncomeRestUserEvent(UserRequest userRequest) {
        String message = "Received request to create user from login: " + userRequest.getLogin();
        logUserEvent(message);
    }

    @Around("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)))")
    private Object logOutgoingRestUserEvent(ProceedingJoinPoint proceedingJoinPoint){
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }

    void logUserEvent(String message) {
        LOG.info(message);
    }

}
