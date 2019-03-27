package com.gojek.parkinglot;

import com.gojek.parkinglot.exception.ParkingLotRuntimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCreateParkingLotOnSuccess() {
        ParkingLot parkingLot = new ParkingLot(5);
        assertEquals(5, parkingLot.getSize());
    }

    @Test
    public void testCreateParkingLotOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        new ParkingLot(-5);
    }

    @Test
    public void testParkOnSuccess() {
        ParkingLot parkingLot = new ParkingLot(3);
        assertEquals(1, parkingLot.park(new Car("KA-01-HH-0001", "Red")));
        assertEquals(2, parkingLot.park(new Car("KA-01-HH-0002", "Green")));
        assertEquals(new Car("KA-01-HH-0001", "Red"), parkingLot.getCar(1));
        assertEquals(new Car("KA-01-HH-0002", "Green"), parkingLot.getCar(2));
    }

    @Test
    public void testGetCarSlotNotOccupiedOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(2);
        assertEquals(1, parkingLot.park(new Car("KA-01-HH-0001", "Red")));
        parkingLot.getCar(2);
    }

    @Test
    public void testGetCarSlotNumberOutOfRangeLowerBoundOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(2);
        assertEquals(1, parkingLot.park(new Car("KA-01-HH-0001", "Red")));
        parkingLot.getCar(0);
    }

    @Test
    public void testGetCarSlotNumberOutOfRangeUpperBoundOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(2);
        assertEquals(1, parkingLot.park(new Car("KA-01-HH-0001", "Red")));
        parkingLot.getCar(3);
    }

    @Test
    public void testGetCarsOnSuccess() {
        ParkingLot parkingLot = new ParkingLot(3);
        assertEquals(1, parkingLot.park(new Car("KA-01-HH-0001", "Red")));
        assertEquals(2, parkingLot.park(new Car("KA-01-HH-0002", "Green")));
        assertEquals(3, parkingLot.park(new Car("KA-01-HH-0003", "Blue")));
        Map<Integer, Car> expectedCars = new LinkedHashMap<>();
        expectedCars.put(1, new Car("KA-01-HH-0001", "Red"));
        expectedCars.put(2, new Car("KA-01-HH-0002", "Green"));
        expectedCars.put(3, new Car("KA-01-HH-0003", "Blue"));
        assertEquals(expectedCars, parkingLot.getCars());
    }

    @Test
    public void testParkFullOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.park(new Car("KA-01-HH-0002", "Green"));
        parkingLot.park(new Car("KA-01-HH-0003", "Blue"));
    }

    @Test
    public void testUnparkOnSuccess() {
        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.park(new Car("KA-01-HH-0002", "Green"));
        parkingLot.park(new Car("KA-01-HH-0003", "Blue"));
        assertTrue(parkingLot.isSlotOccupied(2));
        assertEquals(new Car("KA-01-HH-0002", "Green"), parkingLot.unpark(2));
        assertFalse(parkingLot.isSlotOccupied(2));
        assertEquals(new Car("KA-01-HH-0001", "Red"), parkingLot.getCar(1));
        assertEquals(new Car("KA-01-HH-0003", "Blue"), parkingLot.getCar(3));
    }

    @Test
    public void testUnparkSlotNotOccupiedOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.park(new Car("KA-01-HH-0002", "Green"));
        parkingLot.unpark(1);
        parkingLot.unpark(1);
    }

    @Test
    public void testUnparkSlotNumberOutOfRangeLowerBoundOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.unpark(0);
    }

    @Test
    public void testUnparkSlotNumberOutOfRangeUpperBoundOnException() {
        expectedException.expect(ParkingLotRuntimeException.class);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car("KA-01-HH-0001", "Red"));
        parkingLot.unpark(2);
    }
}
