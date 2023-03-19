package com.springframework.aop;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 15:01:11
 */
public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
