package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月16日 22:26:01
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 二级缓存
     */
    private Map<String, Object> earlySingletonObjects = new HashMap<>();

    /**
     * 三级缓存
     */
    private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if(bean == null) {
            bean = earlySingletonObjects.get(beanName);
            if(bean == null) {
                ObjectFactory<?> factory = singletonFactories.get(beanName);
                if(factory != null) {
                    bean = factory.getObject();
                    //从三级缓存放入二级缓存
                    earlySingletonObjects.put(beanName, bean);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return bean;
    }

    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    public void registerDisposableBean(String beanName, DisposableBean disposableBean) {
        disposableBeans.put(beanName, disposableBean);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        singletonFactories.put(beanName, singletonFactory);
    }

    public void destroySingletons() {
        ArrayList<String> beanNames = new ArrayList<>(disposableBeans.keySet());
        for(String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch(Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
