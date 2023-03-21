package org.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.*;

import java.lang.reflect.Method;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月16日 22:45:15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        //如果Bean需要生成代理对象直接生成代理对象并返回
        Object proxy = resolveBeforeInstantiation(beanName, beanDefinition);
        if(proxy != null) {
            return proxy;
        }
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 执行InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation 如果Bean需要生成代理对象 返回代理对象
     * @param beanName
     * @param beanDefinition
     * @return proxy
     */
    private Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object proxy = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if(proxy != null) {
            proxy = applyBeanPostProcessorsAfterInitialization(proxy, beanName);
        }
        return proxy;
    }

    /**
     * 执行Bean实例化之前的前置处理
     *
     * @param beanClass
     * @param beanName
     * @return
     */
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class beanClass, String beanName) {
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object proxy = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if(proxy != null) {
                    return proxy;
                }
            }
        }
        return null;
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition);

            //解决循环依赖问题 将实例化后的Bean直接放入单例缓存中 提前暴露引用
            if(beanDefinition.isSingleton()) {
                Object finalBean = bean;
                addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, finalBean));
            }

            //实例化Bean后执行
            boolean continueWithPropertyPopulation = applyBeanPostProcessorsAfterInstantiation(beanName, bean);
            if(!continueWithPropertyPopulation) {
                return bean;
            }

            //在设置bean属性之前 运行BeanPostProcessor修改属性值
            applyBeanPostprocessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);

            //填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            //执行bean的初始化方法以及BeanPostProcessor的前置后置处理器
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch(Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }
        //注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        Object exposedObject = bean;
        if(beanDefinition.isSingleton()) {
            //如果有代理对象 此处获取代理对象
            exposedObject = getSingleton(beanName);
            addSingleton(beanName, exposedObject);
        }
        return bean;
    }

    protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean) {
        Object exposedObject = bean;
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                exposedObject = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference(exposedObject, beanName);
                if(exposedObject == null) {
                    return null;
                }
            }
        }
        return exposedObject;
    }

    private boolean applyBeanPostProcessorsAfterInstantiation(String beanName, Object bean) {
        boolean continueWithPropertyPopulation = true;
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                if(!((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessAfterInstantiation(bean, beanName)) {
                    continueWithPropertyPopulation = false;
                    break;
                }
            }
        }
        return continueWithPropertyPopulation;
    }

    protected void applyBeanPostprocessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for(BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if(beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues propertyValues = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if(propertyValues != null) {
                    for(PropertyValue propertyValue : propertyValues.getPropertyValuesList()) {
                        beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                    }
                }
            }
        }
    }

    /**
     * 注册有销毁方法的bean 即bean继承自DisposableBean或有自定义的销毁方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if(beanDefinition.isSingleton()) {
            if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
            }
        }
    }

    /**
     * 实例化Bean
     * @param beanDefinition
     * @return
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) {
         return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 为Bean填充属性
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for(PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValuesList()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if(value instanceof BeanReference) {
                    BeanReference reference = (BeanReference) value;
                    value = getBean(reference.getBeanName());
                }
                //通过反射设置属性
                BeanUtil.setFieldValue(bean, name, value);
            }
        }catch(Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        //为该BeanFactoryAware实现类设置对应的BeanFactory 使其能够感知到BeanFactory
        if(bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        //执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        //执行bean的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch(Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        //执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if(current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }



    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if(current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 执行bean的初始化方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if(bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if(initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }
}
