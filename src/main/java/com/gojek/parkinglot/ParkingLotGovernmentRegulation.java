package com.gojek.parkinglot;

import java.util.List;
import java.util.Optional;

public interface ParkingLotGovernmentRegulation {
    List<String> getRegistrationNumbersByColour(String colour);

    Optional<Integer> getSlotNumberByRegistrationNumber(String registrationNumber);

    List<Integer> getSlotNumbersByColour(String colour);
}
