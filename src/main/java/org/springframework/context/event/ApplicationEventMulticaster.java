package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventListener;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:57:19
 */
public interface ApplicationEventMulticaster{

    void addApplicationListener(ApplicationEventListener<?> listener);

    void removeApplicationListener(ApplicationEventListener<?> listener);

    /**
     * 广播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
