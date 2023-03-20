package org.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * 实现该接口 能够感知所属的BeanFactory
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 21:31:58
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
