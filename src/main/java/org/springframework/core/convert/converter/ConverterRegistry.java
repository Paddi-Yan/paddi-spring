package org.springframework.core.convert.converter;

/**
 * 类型转换器注册接口
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 21:40:12
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);
}
