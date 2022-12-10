package com.edureka.userms.service;

public class Calculator {
    private final MyClass myClass;

    public Calculator(MyClass myClass) {
        this.myClass = myClass;
    }

    public int add(int a, int b) {
        int c = myClass.enrich(a); // mutant-1: remove this line
        int d = myClass.enrich(b); // mutant-2: remove this line
//        myClass.enrich(b); //  mutant-3: remove this line
        return c + d;

    }
}
