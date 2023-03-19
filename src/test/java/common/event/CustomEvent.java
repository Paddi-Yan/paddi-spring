package common.event;

import com.springframework.context.event.ApplicationContextEvent;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 23:22:20
 */
public class CustomEvent extends ApplicationContextEvent {
    public CustomEvent(Object source) {
        super(source);
    }
}
