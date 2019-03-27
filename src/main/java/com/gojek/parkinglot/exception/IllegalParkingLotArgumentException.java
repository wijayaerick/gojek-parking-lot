package com.gojek.parkinglot.exception;

public class IllegalParkingLotArgumentException extends ParkingLotRuntimeException {
    public IllegalParkingLotArgumentException(String message) {
        super(message);
    }
}
