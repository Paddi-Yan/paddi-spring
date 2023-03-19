package com.springframework.context.event;

import com.springframework.beans.BeansException;
import com.springframework.beans.factory.BeanFactory;
import com.springframework.context.ApplicationEvent;
import com.springframework.context.ApplicationEventListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:59:19
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for(ApplicationEventListener<ApplicationEvent> applicationEventListener : applicationEventListeners) {
            if(supportEvent(applicationEventListener, event)) {
                applicationEventListener.onApplicationEvent(event);
            }
        }
    }

    protected boolean supportEvent(ApplicationEventListener<ApplicationEvent> applicationEventListener, ApplicationEvent event) {
        Type type = applicationEventListener.getClass().getGenericInterfaces()[0];
        Type actualArgumentType = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualArgumentType.getTypeName();
        Class<?> eventClass;
        try {
            eventClass = Class.forName(className);
        } catch(ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        return eventClass.isAssignableFrom(event.getClass());
    }
}
