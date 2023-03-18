package bean;

import com.springframework.beans.factory.FactoryBean;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月18日 22:37:21
 */
public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    @Override
    public boolean isSingleton() {
        //return true;
        return false;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}