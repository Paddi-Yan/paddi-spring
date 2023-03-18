package com.springframework.beans.factory;

import com.springframework.beans.BeansException;

/**
 * Bean容器
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月16日 22:04:01
 */
public interface BeanFactory {
    /**
     * 获取Bean
     * @param name
     * @return
     * @throws BeansException
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据名称和类型查找Bean
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
