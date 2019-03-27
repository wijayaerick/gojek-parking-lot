package com.gojek.parkinglot;

import com.gojek.parkinglot.command.ParkingLotCommandExecutor;

public class Application {
    public static void main(String[] args) {
        new ParkingLotCommandExecutor(args).run();
    }
}
