package com.springframework.beans.factory.config;

import com.springframework.beans.BeansException;
import com.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许自定义修改BeanDefinition的属性值
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 11:50:58
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加载完成之后并且在Bean初始化之前 提供修改BeanDefinition的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
