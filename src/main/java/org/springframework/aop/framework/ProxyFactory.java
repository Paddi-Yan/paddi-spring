package org.springframework.aop.framework;

import org.springframework.aop.AdvisedSupport;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 14:32:51
 */
public class ProxyFactory extends AdvisedSupport{

    public ProxyFactory() {
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if(this.isProxyTargetClass() || this.getTargetSource().getTargetClass().length == 0) {
            return new CglibAopProxy(this);
        }
        return new JdkDynamicAopProxy(this);
    }
}
