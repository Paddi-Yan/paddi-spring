package org.springframework.aop.aspectj;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 15:02:53
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return this.pointcut;
    }
}
