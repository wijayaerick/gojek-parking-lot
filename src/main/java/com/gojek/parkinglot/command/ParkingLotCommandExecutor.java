package com.gojek.parkinglot.command;

import com.gojek.parkinglot.Car;
import com.gojek.parkinglot.ParkingLot;
import com.gojek.parkinglot.exception.IllegalParkingLotArgumentException;
import com.gojek.parkinglot.exception.ParkingLotSlotNumberOutOfRangeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ParkingLotCommandExecutor implements ParkingLotCommand, ParkingLotCommandGovernmentRegulation {
    private ParkingLot parkingLot;
    private Scanner scanner;
    private PrintWriter writer;
    private boolean exit = false;

    private static final String CREATE_PARKING_LOT_CMD = "create_parking_lot";
    private static final String PARK_CMD = "park";
    private static final String LEAVE_CMD = "leave";
    private static final String STATUS_CMD = "status";
    private static final String EXIT_CMD = "exit";
    private static final String REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR_CMD =
            "registration_numbers_for_cars_with_colour";
    private static final String SLOT_NUMBERS_FOR_CARS_WITH_COLOUR_CMD = "slot_numbers_for_cars_with_colour";
    private static final String SLOT_NUMBER_FOR_REGISTRATION_NUMBER_CMD = "slot_number_for_registration_number";
    private static final String MUST_RUN_CREATE_PARKING_LOT_FIRST = CREATE_PARKING_LOT_CMD
            + " must be successfully executed first";
    private static final String NOT_FOUND = "Not found";

    public ParkingLotCommandExecutor(String[] args) {
        initializeInputOutput(args);
    }

    private void initializeInputOutput(String[] args) {
        if (args.length > 0) {
            try {
                scanner = new Scanner(new FileInputStream(new File(args[0])));
                scanner.useDelimiter("\\s");
            } catch (FileNotFoundException e) {
                System.out.println("Cannot read file: " + args[0]);
                exit();
            }
        } else {
            scanner = new Scanner(System.in);
        }
        writer = new PrintWriter(System.out, true);
    }

    public void run() {
        while (!exit) {
            if (!scanner.hasNext()) break;
            runCommand(scanner.next());
        }
        closeInputOutput();
    }

    private void runCommand(String command) {
        if (CREATE_PARKING_LOT_CMD.equals(command)) {
            createParkingLot(scanner.nextInt());
        } else if (PARK_CMD.equals(command)) {
            String registrationNumber = scanner.next();
            String colour = scanner.next();
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                park(registrationNumber, colour);
            }
        } else if (LEAVE_CMD.equals(command)) {
            int slotNumber = scanner.nextInt();
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                leave(slotNumber);
            }
        } else if (STATUS_CMD.equals(command)) {
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                status();
            }
        } else if (EXIT_CMD.equals(command)) {
            exit();
        } else if (REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR_CMD.equals(command)) {
            String colour = scanner.next();
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                registrationNumbersForCarsWithColour(colour);
            }
        } else if (SLOT_NUMBERS_FOR_CARS_WITH_COLOUR_CMD.equals(command)) {
            String colour = scanner.next();
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                slotNumbersForCarsWithColour(colour);
            }
        } else if (SLOT_NUMBER_FOR_REGISTRATION_NUMBER_CMD.equals(command)) {
            String registrationNumber = scanner.next();
            if (parkingLot == null) {
                writer.println(MUST_RUN_CREATE_PARKING_LOT_FIRST);
            } else {
                slotNumberForRegistrationNumber(registrationNumber);
            }
        } else {
            writer.println("Command not found: " + command);
        }
    }

    private void closeInputOutput() {
        try {
            writer.close();
            scanner.close();
        } catch (Exception ignored) {
            // intentionally blank
        }
    }

    @Override
    public void createParkingLot(int size) {
        try {
            parkingLot = new ParkingLot(size);
            writer.println("Created a parking lot with " + size + " slots");
        } catch (IllegalParkingLotArgumentException e) {
            writer.println("Failed to create a parking lot because: " + e.getMessage());
        }
    }

    @Override
    public void park(String registrationName, String colour) {
        if (parkingLot.isFull()) {
            writer.println("Sorry, parking lot is full");
        } else {
            writer.println("Allocated slot number: " + parkingLot.park(new Car(registrationName, colour)));
        }
    }

    @Override
    public void leave(int slotNumber) {
        try {
            if (parkingLot.isSlotOccupied(slotNumber)) {
                parkingLot.unpark(slotNumber);
                writer.println("Slot number " + slotNumber + " is free");
            } else {
                writer.println("Sorry, slot number " + slotNumber + " is already free");
            }
        } catch (ParkingLotSlotNumberOutOfRangeException e) {
            writer.println("Slot number is out of range: " + e.getMessage());
        }
    }

    @Override
    public void status() {
        StringBuilder builder = new StringBuilder("Slot No.    Registration No    Colour");
        parkingLot.getCars().forEach((slotNumber, car) -> builder.append("\n").append(slotNumber)
                .append("           ").append(car.getRegistrationNumber()).append("      ").append(car.getColour()));
        writer.println(builder.toString());
    }

    @Override
    public void exit() {
        exit = true;
    }

    @Override
    public void registrationNumbersForCarsWithColour(String colour) {
        List<String> registrationNumbers = parkingLot.getRegistrationNumbersByColour(colour);
        if (registrationNumbers.isEmpty()) {
            writer.println(NOT_FOUND);
        } else {
            writer.println(String.join(", ", registrationNumbers));
        }
    }

    @Override
    public void slotNumberForRegistrationNumber(String registrationNumber) {
        Optional<Integer> slotNumber = parkingLot.getSlotNumberByRegistrationNumber(registrationNumber);
        writer.println(slotNumber.isPresent() ? slotNumber.get() : NOT_FOUND);
    }

    @Override
    public void slotNumbersForCarsWithColour(String colour) {
        List<Integer> slotNumbers = parkingLot.getSlotNumbersByColour(colour);
        if (slotNumbers.isEmpty()) {
            writer.println(NOT_FOUND);
        } else {
            writer.println(String.join(", ", slotNumbers.stream().map(String::valueOf)
                    .collect(Collectors.toList())));
        }
    }
}
