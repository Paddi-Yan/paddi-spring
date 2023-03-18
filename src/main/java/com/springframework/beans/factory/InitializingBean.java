package com.springframework.beans.factory;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 18:03:00
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
