# Parking Lot

Gojek Junior Full-Stack Engineer Coding Test

* [Description](#description)
  - [Command List](#command-list)
  - [Illegal Operation](#illegal-operation)
* [Build Instruction](#build-instruction)
* [Run Instruction](#run-instruction)
* [Development](#development)

## Description

Parking Lot is a simple application that accepts string-based commands to control parking lot system. 
You can run the application with interactive command prompt based shell or with file that contains 
commands. 

### Command List

Basic commands: 

* `create_parking_lot <PARKING_LOT_SIZE>`
* `park <CAR_REGISTRATION_NUMBER> <CAR_COLOUR>`
* `leave <SLOT_NUMBER>`
* `status`
* `exit`

Query commands:

* `registration_numbers_for_cars_with_colour <CAR_COLOUR>`
* `slot_numbers_for_cars_with_colour <CAR_COLOUR>`
* `slot_number_for_registration_number <CAR_REGISTRATION_NUMBER>`

### Illegal Operation

User is assumed to input with correct arguments. For example, `leave` will exactly accept an integer. 
Other than that, the system should be able to handle illegal operations such as:
* `park` when parking lot is full
* `leave` an already empty slot, or using out-of-range slot number
* `create_parking_lot` with size lower than 1
* invoking unlisted command
* accessing parking lot (park, status, etc) before successfully invoked `create_parking_lot`

## Build Instruction

Run `bin/setup` to build the project with dependencies and run tests.

```
parking_lot $ bin/setup
```

## Run Instruction

Run ```bin/parking_lot``` to start the application. It accepts one optional argument for input file path. You should build the project before running the application.

```
parking_lot $ bin/parking_lot <OPTIONAL_FILE_PATH>
```

You can also run Rspec-based testing suite by running `bin/run_functional_tests`.

```
parking_lot $ bin/run_functional_tests
```

For more information about the testing suite, see [functional_spec](functional_spec).

## Development

To develop this project, you will need:
* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or higher
* [Gradle](https://gradle.org/)

Additionaly, you will need [Ruby](https://www.ruby-lang.org/en/documentation/installation/) to run functional_spec. 
