package org.springframework.beans.factory;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 20:13:57
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
