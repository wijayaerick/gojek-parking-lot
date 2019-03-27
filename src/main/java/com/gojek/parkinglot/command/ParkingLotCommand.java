package com.gojek.parkinglot.command;

public interface ParkingLotCommand {
    void createParkingLot(int size);

    void park(String registrationName, String colour);

    void leave(int slotNumber);

    void status();

    void exit();
}
