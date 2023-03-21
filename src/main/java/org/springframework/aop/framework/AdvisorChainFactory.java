package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月21日 11:17:08
 */
public interface AdvisorChainFactory {
    List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport config, Method method, Class<?> targetClass);
}
