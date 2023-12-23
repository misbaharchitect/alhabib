package com.edureka.userms.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    @Mock
    private MyClass myClassMock;

    @Test
    void add_solitary() {
        MockitoAnnotations.initMocks(this);
        Mockito
                .when(myClassMock.enrich(ArgumentMatchers.anyInt()))
                .thenReturn(5);

        final Calculator calculator = new Calculator(myClassMock);

        final int actualResult = calculator.add(2, 3);
        assertEquals(10, actualResult);
    }

    @Test
    void add_sociable() {
        final MyClass myClass = new MyClass();
        final Calculator calculator = new Calculator(myClass);

        final int actualResult = calculator.add(2, 3);
        assertEquals(9, actualResult);
    }
}