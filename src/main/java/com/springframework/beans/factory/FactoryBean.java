package com.springframework.beans.factory;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:29:17
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();
}
