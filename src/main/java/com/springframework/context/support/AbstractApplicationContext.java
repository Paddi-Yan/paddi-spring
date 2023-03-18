package com.springframework.context.support;

import com.springframework.beans.BeansException;
import com.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.springframework.beans.factory.config.BeanFactoryPostProcessor;
import com.springframework.beans.factory.config.BeanPostProcessor;
import com.springframework.context.ConfigurableApplicationContext;
import com.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 13:35:44
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        //创建BeanFactory并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory =  getBeanFactory();

        //在bean实例化之前执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor需要在其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 注册BeanPostProcessor
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 在bean实例化之前 执行BeanFactoryPostProcessors
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 创建BeanFactory并加载BeanDefinition
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void close() {
        doClose();
    }

    protected void doClose() {
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this :: doClose));
    }
}
