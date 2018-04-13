package com.spand.bridgecom.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {

    protected static final Map<String, Integer> accessByNameCounter   = new HashMap<>();

    @Pointcut("(execution(* com.spand.bridgecom.service.UserService.findUserByName(String, ..)) && args(eventName, ..))")
    private void accessedByName(String eventName) {}

    @Before("accessedByName(eventName)")
    public void countAccessByName(String eventName) {
        increaseCounter(accessByNameCounter, eventName);
    }

    private <K> void increaseCounter(Map<K, Integer> stat, K key) {
        stat.put(key, stat.getOrDefault(key, 0) + 1);
    }

}
