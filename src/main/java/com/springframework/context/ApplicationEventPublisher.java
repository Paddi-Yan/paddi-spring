package com.springframework.context;

/**
 * 事件发布者接口
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:47:20
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     *
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
