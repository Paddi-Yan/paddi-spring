package org.springframework.beans.factory.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 15:27:11
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在Bean实例化之前执行
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * Bean实例化之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * Bean实例化之后 设置属性值之前执行
     * @param propertyValues
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeansException;

    /**
     * 提前暴露Bean引用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
