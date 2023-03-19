package ioc;

import bean.Car;
import com.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:37:40
 */
public class FactoryBeanTest {
    @Test
    public void testFactoryBean() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");

        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        System.out.println(car1.equals(car2));
        assertThat(car1.getBrand()).isEqualTo("porsche");
        assertThat(car1.equals(car2)).isEqualTo(false);
    }
}
