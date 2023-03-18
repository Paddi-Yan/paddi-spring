package com.springframework.beans.factory.support;

import com.springframework.beans.BeansException;
import com.springframework.core.io.Resource;
import com.springframework.core.io.ResourceLoader;

/**
 * 读取Bean定义信息即BeanDefinition的接口
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:50:56
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;
}
