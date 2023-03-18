package com.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月17日 09:46:24
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValuesList = new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        for(int i = 0; i < propertyValuesList.size(); i++) {
            PropertyValue currentPropertyValue = propertyValuesList.get(i);
            if(currentPropertyValue.getName().equals(propertyValue.getName())) {
                //覆盖原有属性值
                this.propertyValuesList.set(i, propertyValue);
                return;
            }
        }
        this.propertyValuesList.add(propertyValue);
    }


    public PropertyValue[] getPropertyValuesList() {
        return this.propertyValuesList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for(PropertyValue propertyValue : propertyValuesList) {
            if(propertyValue.getValue().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }
}
