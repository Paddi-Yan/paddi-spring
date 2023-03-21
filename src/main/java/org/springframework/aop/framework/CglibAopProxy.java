package org.springframework.aop.framework;

import cn.hutool.core.collection.CollectionUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

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

    /**
     * 注意此处的MethodInterceptor是cglib中的接口，advised中的MethodInterceptor的AOP中定义的接口，因此定义此类做适配
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport support;

        private DynamicAdvisedInterceptor(AdvisedSupport support) {
            this.support = support;
        }

        @Override
        public Object intercept(Object proxy, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
            Object target = support.getTargetSource().getTarget();
            Class<?> targetClass = target.getClass();
            Object returnVal = null;
            List<Object> interceptorsChain = this.support.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(proxy, target, method, arguments, targetClass, interceptorsChain, methodProxy);
            if(CollectionUtil.isEmpty(interceptorsChain)) {
                returnVal = methodProxy.invoke(target, arguments);
            }else {
                returnVal = methodInvocation.proceed();
            }
            return returnVal;
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy proxy;

        public CglibMethodInvocation(Object proxy, Object target, Method method,
                                     Object[] arguments, Class<?> targetClass,
                                     List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
            super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
            this.proxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return super.proceed();
        }
    }
}
