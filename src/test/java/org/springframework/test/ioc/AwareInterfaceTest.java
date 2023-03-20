package org.springframework.test.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import service.HelloService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 21:48:00
 */
public class AwareInterfaceTest {
    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        assertThat(helloService.getApplicationContext()).isNotNull();
        assertThat(helloService.getBeanFactory()).isNotNull();
    }
}
