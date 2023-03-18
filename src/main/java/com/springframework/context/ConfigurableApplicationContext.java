package com.springframework.context;

import com.springframework.beans.BeansException;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 13:34:04
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;

    /**
     * 关闭应用上下文
     */
    void close();

    /**
     * 向虚拟机进程注册一个钩子方法 在虚拟机关闭之前执行关闭容器等操作
     */
    void registerShutdownHook();

}
