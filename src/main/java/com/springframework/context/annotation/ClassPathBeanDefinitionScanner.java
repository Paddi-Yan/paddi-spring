package com.springframework.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.springframework.beans.factory.config.BeanDefinition;
import com.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 15:29:13
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider{

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for(String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for(BeanDefinition candidate : candidates) {
                //解析Bean的作用域
                String scope = resolveBeanScope(candidate);
                if(StrUtil.isNotEmpty(scope)) {
                    candidate.setScope(scope);
                }
                //生成Bean的名称
                String beanName = determineBeanName(candidate);
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
    }

    /**
     * 解析Bean的作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if(scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 生成Bean的名称
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if(StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
