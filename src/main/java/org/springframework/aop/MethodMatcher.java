package org.springframework.aop;

import java.lang.reflect.Method;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 13:13:23
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
