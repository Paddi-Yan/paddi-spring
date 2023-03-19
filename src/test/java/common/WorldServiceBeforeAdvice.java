package common;

import com.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 14:44:45
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("WorldServiceBeforeAdvice: do something before the earth explodes");
    }
}
