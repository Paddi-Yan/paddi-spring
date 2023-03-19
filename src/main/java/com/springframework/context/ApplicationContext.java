package com.springframework.context;

import com.springframework.beans.factory.HierarchicalBeanFactory;
import com.springframework.beans.factory.ListableBeanFactory;
import com.springframework.core.io.ResourceLoader;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 13:30:38
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher{
}
