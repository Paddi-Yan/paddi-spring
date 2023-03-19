package common.event;

import com.springframework.context.ApplicationEventListener;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 23:21:47
 */
public class CustomEventListener implements ApplicationEventListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }
}
