package org.springframework.aop;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 13:12:35
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);

}
