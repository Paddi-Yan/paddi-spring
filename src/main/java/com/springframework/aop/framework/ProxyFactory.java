package com.springframework.aop.framework;

import com.springframework.aop.AdvisedSupport;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月19日 14:32:51
 */
public class ProxyFactory {

    private AdvisedSupport support;

    public ProxyFactory(AdvisedSupport support) {
        this.support = support;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if(support.isProxyTargetClass()) {
            return new CglibAopProxy(support);
        }
        return new JdkDynamicAopProxy(support);
    }
}
