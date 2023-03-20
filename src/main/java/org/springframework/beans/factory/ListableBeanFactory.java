package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

import java.util.Map;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:25:39
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * 返回指定类型的所有实例
     * @param type
     * @return
     * @param <T>
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有Bean名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
