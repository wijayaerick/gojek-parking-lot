package com.gojek.parkinglot.exception;

public class IllegalParkingLotOperationException extends ParkingLotRuntimeException {
    public IllegalParkingLotOperationException(String message) {
        super(message);
    }
}
