package com.spand.bridgecom.aop;

import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)) && args(userRequest, ..))")
    private void createNewUser(UserRequest userRequest) {}

    @Before("createNewUser(userRequest)")
    public void logCreateNewUser(UserRequest userRequest) {
        logCreateNewUserEvent(userRequest);
    }

    private void logCreateNewUserEvent(UserRequest userRequest) {
        LOG.info(userRequest.toString());
    }

}
