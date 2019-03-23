package com.gojek.parkinglot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {
    @Test
    public void testGetRegistrationNumberOnSuccess() {
        Car car = new Car("KA-01-HH-1234", "White");
        assertEquals("KA-01-HH-1234", car.getRegistrationNumber());
    }

    @Test
    public void testGetColourOnSuccess() {
        Car car = new Car("KA-01-HH-1234", "White");
        assertEquals("White", car.getColour());
    }
}
