package org.springframework.test.common.event;

import org.springframework.context.ApplicationEventListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 23:21:44
 */
public class ContextRefreshedEventListener implements ApplicationEventListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(this.getClass().getName());
    }
}