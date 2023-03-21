package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月21日 11:12:52
 */
public interface AfterReturningAdvice extends AfterAdvice{

    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
