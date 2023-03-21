package org.springframework.core.convert.converter;

/**
 * 类型转换抽象接口
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 21:37:31
 */
public interface Converter<S, T> {

    /**
     * 类型转换
     * @param source
     * @return
     */
    T convert(S source);
}
