package com.spand.bridgecom.aop;

import com.spand.bridgecom.model.AppUser;
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

    @Pointcut("(execution(* com.spand.bridgecom.service.UserService.saveUser(Object, ..)) && args(appUser, ..))")
    private void saveUser(AppUser appUser) {}

    @Before("saveUser(appUser)")
    public void logCreateUser(AppUser appUser) {
        logAccessEvent(appUser);
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
