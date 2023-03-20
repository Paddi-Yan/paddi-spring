package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 13:45:04
 */
public class CglibAopProxy implements AopProxy{
    private final AdvisedSupport support;

    public CglibAopProxy(AdvisedSupport support) {
        this.support = support;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(support.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(support.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(support));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport support;

        private DynamicAdvisedInterceptor(AdvisedSupport support) {
            this.support = support;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(support.getTargetSource()
                                                                                           .getTarget(), method, arguments, methodProxy);
            if(support.getMethodMatcher().matches(method, support.getTargetSource().getTarget().getClass())) {
                return support.getMethodInterceptor().invoke(methodInvocation);
            }
            return methodInvocation.proceed();
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy proxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy proxy) {
            super(target, method, arguments);
            this.proxy = proxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return this.proxy.invoke(this.target, this.arguments);
        }
    }
}
