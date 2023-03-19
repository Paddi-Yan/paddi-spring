package com.springframework.context.event;

import com.springframework.beans.BeansException;
import com.springframework.beans.factory.BeanFactory;
import com.springframework.beans.factory.BeanFactoryAware;
import com.springframework.context.ApplicationEvent;
import com.springframework.context.ApplicationEventListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 23:00:18
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationEventListener<ApplicationEvent>> applicationEventListeners = new HashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationEventListener<?> listener) {
        applicationEventListeners.add((ApplicationEventListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationEventListener<?> listener) {
        applicationEventListeners.remove(listener);
    }
}
