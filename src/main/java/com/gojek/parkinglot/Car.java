package com.gojek.parkinglot;

public final class Car {
    private final String registrationNumber;
    private final String colour;

    public Car(String registrationNumber, String colour) {
        this.registrationNumber = registrationNumber;
        this.colour = colour;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColour() {
        return colour;
    }
}
