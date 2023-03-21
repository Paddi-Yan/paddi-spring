package org.springframework.aop.framework;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 13:28:31
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advisedSupport.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取目标对象
        Object target = advisedSupport.getTargetSource().getTarget();
        Class<?> targetClass = target.getClass();
        Object returnVal = null;
        //获取拦截链
        List<Object> interceptorsChain = this.advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        if(CollectionUtil.isEmpty(interceptorsChain)) {
            return method.invoke(target, args);
        }else {
            //将拦截器统一封装为ReflectiveMethodInvocation
            ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, interceptorsChain);
            //执行拦截链
            returnVal = reflectiveMethodInvocation.proceed();
        }
        return returnVal;
    }

}
