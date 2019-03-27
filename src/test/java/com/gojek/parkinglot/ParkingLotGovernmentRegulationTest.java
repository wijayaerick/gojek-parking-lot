package com.gojek.parkinglot;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ParkingLotGovernmentRegulationTest {
    private static ParkingLot parkingLot;

    @BeforeClass
    public static void initializeParkingLot() {
        parkingLot = new ParkingLot(5);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.park(new Car("KA-01-HH-0002", "Green"));
        parkingLot.park(new Car("KA-01-HH-0003", "Blue"));
        parkingLot.park(new Car("KA-01-HH-0004", "Red"));
        parkingLot.park(new Car("KA-01-HH-0005", "Red"));
    }

    @Test
    public void testGetRegistrationNumbersByColourOnSuccess() {
        assertArrayEquals(new String[]{"KA-01-HH-0001", "KA-01-HH-0004", "KA-01-HH-0005"},
                parkingLot.getRegistrationNumbersByColour("Red").toArray());
        assertArrayEquals(new String[]{}, parkingLot.getRegistrationNumbersByColour("White").toArray());
    }

    @Test
    public void testGetSlotNumberByRegistrationNumberOnSuccess() {
        assertEquals(new Integer(1), parkingLot.getSlotNumberByRegistrationNumber("KA-01-HH-0001").get());
        assertFalse(parkingLot.getSlotNumberByRegistrationNumber("KA-01-HH-9999").isPresent());
    }

    @Test
    public void testGetSlotNumbersByColour() {
        assertArrayEquals(new Integer[]{1, 4, 5}, parkingLot.getSlotNumbersByColour("Red").toArray());
        assertArrayEquals(new Integer[]{}, parkingLot.getSlotNumbersByColour("White").toArray());
    }
}
