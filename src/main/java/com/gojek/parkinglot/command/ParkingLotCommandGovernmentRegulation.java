package com.gojek.parkinglot.command;

public interface ParkingLotCommandGovernmentRegulation {
    void registrationNumbersForCarsWithColour(String colour);

    void slotNumberForRegistrationNumber(String registrationNumber);

    void slotNumbersForCarsWithColour(String colour);
}
