package org.springframework.context.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventListener;

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
