package com.gojek.parkinglot;

import com.gojek.parkinglot.exception.ParkingLotRuntimeException;

import java.util.Map;
import java.util.TreeMap;

public class ParkingLot {
    private Map<Integer, Car> cars;
    private final int size;
    private static final int SMALLEST_SLOT_NUMBER = 1;
    private int lowestEmptySlotNumber = SMALLEST_SLOT_NUMBER;

    public ParkingLot(int size) {
        checkSize(size);
        this.size = size;
        this.cars = new TreeMap<>();
    }

    private boolean isSizeValid(int size) {
        return size > 0;
    }

    private void checkSize(int size) {
        if (!isSizeValid(size)) {
            throw new ParkingLotRuntimeException("Size must be at least 1, found size: " + size);
        }
    }

    public int getSize() {
        return size;
    }

    public Car getCar(int slotNumber) {
        if (!isSlotOccupied(slotNumber)) {
            throw new ParkingLotRuntimeException("Attempt to get car from empty parking lot slot: " + slotNumber);
        }
        return cars.get(slotNumber);
    }

    public Map<Integer, Car> getCars() {
        return cars;
    }

    public boolean isFull() {
        return lowestEmptySlotNumber > size;
    }

    public boolean isSlotOccupied(int slotNumber) {
        checkSlotNumberInRange(slotNumber);
        return cars.containsKey(slotNumber);
    }

    public int park(Car car) {
        if (isFull()) {
            throw new ParkingLotRuntimeException("Attempt to park when parking lot is full");
        }
        int allocatedSlotNumber = lowestEmptySlotNumber;
        cars.put(lowestEmptySlotNumber, car);
        updateLowestEmptySlotNumberAfterPark();
        return allocatedSlotNumber;
    }

    private void updateLowestEmptySlotNumberAfterPark() {
        while (!isFull() && cars.containsKey(lowestEmptySlotNumber)) {
            lowestEmptySlotNumber++;
        }
    }

    public Car unpark(int slotNumber) {
        if (!isSlotOccupied(slotNumber)) {
            throw new ParkingLotRuntimeException("Attempt to unpark empty parking lot slot: " + slotNumber);
        }
        Car unparked = cars.remove(slotNumber);
        updateLowestEmptySlotNumberAfterUnpark(slotNumber);
        return unparked;
    }

    private void updateLowestEmptySlotNumberAfterUnpark(int latestUnparkedSlotNumber) {
        lowestEmptySlotNumber = Math.min(lowestEmptySlotNumber, latestUnparkedSlotNumber);
    }

    private boolean isSlotNumberInRange(int slotNumber) {
        return slotNumber >= SMALLEST_SLOT_NUMBER && slotNumber < size + SMALLEST_SLOT_NUMBER;
    }

    private void checkSlotNumberInRange(int slotNumber) {
        if (!isSlotNumberInRange(slotNumber)) {
            throw new ParkingLotRuntimeException("Slot number is out of range: " + slotNumber);
        }
    }
}
