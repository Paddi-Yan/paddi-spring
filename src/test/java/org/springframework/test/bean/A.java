package org.springframework.test.bean;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 22:48:50
 */
public class A {
    private B b;

    public void func(){
        System.out.println("A.func()....");
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
