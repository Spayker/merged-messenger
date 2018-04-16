package com.spand.bridgecom.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("(execution(* com.spand.bridgecom.service.UserService.saveUser(String, ..)) && args(eventName, ..))")
    private void saveUser(String eventName) {}

    @Before("saveUser(eventName)")
    public void logCreateUser(String eventName) {
        logAccessEvent(eventName);
    }

    @Pointcut("(execution(* com.spand.bridgecom.service.UserService.findUserByName(String, ..)) && args(eventName, ..))")
    private void findByName(String eventName) {}

    @Before("findByName(eventName)")
    public void logAccessByName(String eventName) {
        logAccessEvent(eventName);
    }

    private void logAccessEvent(String text) {
        LOG.info(text);
    }

}
