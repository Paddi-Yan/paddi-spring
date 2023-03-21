package org.springframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.AdvisedSupport;
import org.springframework.aop.Advisor;
import org.springframework.aop.PointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月21日 11:18:21
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    @Override
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass) {
        Advisor[] advisors = config.getAdvisors().toArray(new Advisor[0]);
        ArrayList<Object> interceptorList = new ArrayList<>(advisors.length);
        Class<?> actualClass = (targetClass != null) ? targetClass : method.getDeclaringClass();
        for(Advisor advisor : advisors) {
            if(advisor instanceof PointcutAdvisor) {
                //Add it conditionally
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                //校验当前Advisor是否适用于当前对象
                if(pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                    //校验当前Advisor是否适用于当前方法上
                    if(pointcutAdvisor.getPointcut().getMethodMatcher().matches(method, actualClass)) {
                        MethodInterceptor advice = (MethodInterceptor) advisor.getAdvice();
                        interceptorList.add(advice);
                    }
                }
            }
        }
        return interceptorList;
    }
}
