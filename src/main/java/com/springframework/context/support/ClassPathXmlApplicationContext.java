package com.springframework.context.support;

import com.springframework.beans.BeansException;

/**
 * xml文件的应用上下文
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 13:50:59
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}
