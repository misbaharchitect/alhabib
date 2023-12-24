package org.acme;

public class Customer {
    public String name;
    public int age;

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
