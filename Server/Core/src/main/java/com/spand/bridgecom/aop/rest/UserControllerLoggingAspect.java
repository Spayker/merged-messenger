package com.spand.bridgecom.aop.rest;

import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserControllerLoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(UserControllerLoggingAspect.class);

    @Pointcut("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)) && args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {}

    @Before("createNewUser(userRequest)")
    private void logIncomeRestUserEvent(UserRequest userRequest) {
        logUserEvent(userRequest);
    }

    @After("createNewUser(userRequest)")
    private void logOutgoingRestUserEvent(UserRequest userRequest) {
        logUserEvent(userRequest);
    }

    private void logUserEvent(UserRequest userRequest) {
        LOG.info(userRequest.toString());
    }

}
