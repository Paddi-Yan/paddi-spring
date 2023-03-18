package com.springframework.context;

import com.springframework.beans.BeansException;
import com.springframework.beans.factory.Aware;

/**
 * 实现该接口即可感知所属的ApplicationContext
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 21:35:49
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
