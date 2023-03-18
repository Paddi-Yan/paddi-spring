import com.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 21:00:16
 */
public class InitAndDestroyMethodTest {
    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        applicationContext.registerShutdownHook();  //或者手动关闭 applicationContext.close();
    }
}
