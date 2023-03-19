package common.event;

import com.springframework.context.ApplicationEventListener;
import com.springframework.context.event.ContextClosedEvent;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 23:21:50
 */
public class ContextClosedEventListener implements ApplicationEventListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}