package org.springframework.context;

import java.util.EventListener;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:48:08
 */
public interface ApplicationEventListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
