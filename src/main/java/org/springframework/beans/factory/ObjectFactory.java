package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 23:12:07
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
