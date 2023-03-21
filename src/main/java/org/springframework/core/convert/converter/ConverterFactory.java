package org.springframework.core.convert.converter;

/**
 * 类型转换接口
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 21:38:35
 */
public interface ConverterFactory<S, R> {

    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
