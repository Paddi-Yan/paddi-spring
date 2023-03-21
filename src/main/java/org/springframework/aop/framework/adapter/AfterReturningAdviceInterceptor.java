package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterAdvice;
import org.springframework.aop.AfterReturningAdvice;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月21日 11:24:31
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {
    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor() {
    }

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object resultVal = methodInvocation.proceed();
        this.advice.afterReturning(resultVal, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return resultVal;
    }

    public AfterReturningAdvice getAdvice() {
        return advice;
    }

    public void setAdvice(AfterReturningAdvice advice) {
        this.advice = advice;
    }
}
