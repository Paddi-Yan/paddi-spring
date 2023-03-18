package com.springframework.beans.factory.config;

import com.springframework.beans.BeansException;

/**
 * 用于修改实例化后的bean的修改拓展点
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 11:48:33
 */
public interface BeanPostProcessor {

    /**
     * 在bean执行初始化方法之前执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean执行初始化方法之后执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
