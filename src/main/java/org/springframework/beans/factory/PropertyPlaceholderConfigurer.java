package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * 在Bean实例化之前 编辑BeanDefinition 解析XML中的占位符 使用Property配置值替换占位符
 *
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 11:32:49
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //加载属性配置文件
        Properties properties = loadProperties();

        //属性值替换占位符
        processProperties(beanFactory, properties);

        //往容器里添加字符串解析器 解析@Value
        PlaceHolderResolvingStringValueResolver resolver = new PlaceHolderResolvingStringValueResolver(properties);
        beanFactory.addEmbeddedValueResolver(resolver);
    }

    private Properties loadProperties() {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Properties properties = new Properties();
        Resource resource = resourceLoader.getResource(location);
        try {
            properties.load(resource.getInputStream());
            return properties;
        } catch(IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            resolvePropertyValue(beanDefinition, properties);
        }
    }

    private void resolvePropertyValue(BeanDefinition beanDefinition, Properties properties) {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for(PropertyValue propertyValue : propertyValues.getPropertyValuesList()) {
            Object value = propertyValue.getValue();
            if(value instanceof String) {
                //TODO 只简单支持一个占位符的格式
                String strVal = (String) value;
                StringBuffer stringBuffer = new StringBuffer(strVal);
                int startIndex = strVal.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strVal.indexOf(PLACEHOLDER_SUFFIX);
                if(startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String propKey = strVal.substring(startIndex + 2, endIndex);
                    String propVal = properties.getProperty(propKey);
                    stringBuffer.replace(startIndex, endIndex + 1,propVal);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), stringBuffer.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String resolvePlaceHolder(String value, Properties properties) {
        //TODO 仅支持一个占位符的格式
        String strValue = value;
        StringBuffer buffer = new StringBuffer(strValue);
        int startIndex = strValue.indexOf(PLACEHOLDER_PREFIX);
        int endIndex = strValue.indexOf(PLACEHOLDER_SUFFIX);
        if(startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            String propKey = strValue.substring(startIndex + 2, endIndex);
            String propVal = properties.getProperty(propKey);
            buffer.replace(startIndex, endIndex + 1, propVal);
        }
        return buffer.toString();
    }

    private class PlaceHolderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        private PlaceHolderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceHolder(strVal, properties);
        }
    }
}
