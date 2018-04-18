package com.spand.bridgecom.aop;

import com.spand.bridgecom.exception.aop.MapperAspectException;
import com.spand.bridgecom.rest.model.UserRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.AfterThrowing;
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
    private void logIncomeRestUser(UserRequest userRequest) {
        String message = "Received request to create user from login: " + userRequest.getLogin();
        logInfoMessage(message);
    }

    @Around("(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)))")
    private Object logOutgoingRestUser(ProceedingJoinPoint proceedingJoinPoint){
        Object value;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new MapperAspectException(e);
        }
        return value;
    }

    @AfterThrowing(pointcut = "(execution(public * com.spand.bridgecom.rest.controller.UserRestController.*(..)))",
                    throwing = "ex")
    public void logException(Exception ex){
        logErrorMessage(ex.getMessage());
    }

    void logInfoMessage(String message) {
        LOG.info(message);
    }

    void logErrorMessage(String message) {
        LOG.info(message);
    }

    void logDebugMessage(String message){
        LOG.debug(message);
    }

}
