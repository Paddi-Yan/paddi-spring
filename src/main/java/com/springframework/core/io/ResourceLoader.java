package com.springframework.core.io;

/**
 * 资源加载器接口
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 10:13:29
 */
public interface ResourceLoader {

    Resource getResource(String location);
}
