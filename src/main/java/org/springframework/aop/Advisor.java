package org.springframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 15:01:40
 */
public interface Advisor {
    Advice getAdvice();
}
