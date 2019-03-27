package com.gojek.parkinglot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CarTest {
    @Test
    public void testGetRegistrationNumberOnSuccess() {
        Car car = new Car("KA-01-HH-1234", "White");
        assertEquals("KA-01-HH-1234", car.getRegistrationNumber());
        assertNotEquals("KB-01-HH-1234", car.getRegistrationNumber());
    }

    @Test
    public void testGetColourOnSuccess() {
        Car car = new Car("KA-01-HH-1234", "White");
        assertEquals("White", car.getColour());
        assertNotEquals("Green", car.getColour());
    }

    @Test
    public void testCarEqualsOnSuccess() {
        assertEquals(new Car("KA-01-HH-1234", "White"),
                new Car("KA-01-HH-1234", "White"));
        assertNotEquals(new Car("KB-01-HH-1234", "Green"),
                new Car("KA-01-HH-1234", "White"));
    }
}
