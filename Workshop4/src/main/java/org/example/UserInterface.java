package org.example;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner = new Scanner(System.in);
    public UserInterface() {
        dealership = DealershipFileManager.getDealership();
    }

    public void display() {

        boolean running = true;

        while (running) {

            System.out.println("\n===== CAR DEALERSHIP =====");
            System.out.println("1 - Find vehicles by price");
            System.out.println("2 - Find vehicles by make/model");
            System.out.println("3 - Find vehicles by year");
            System.out.println("4 - Find vehicles by color");
            System.out.println("5 - Find vehicles by mileage");
            System.out.println("6 - Find vehicles by type");
            System.out.println("7 - List all vehicles");
            System.out.println("8 - Add a vehicle");
            System.out.println("9 - Remove a vehicle");
            System.out.println("99 - Quit");

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> System.out.println("Search by price");
                case "2" -> System.out.println("Search by make/model");
                case "3" -> System.out.println("Search by year");
                case "4" -> System.out.println("Search by color");
                case "5" -> System.out.println("Search by mileage");
                case "6" -> System.out.println("Search by type");

                case "7" -> show(dealership.getAllVehicles());

                case "8" -> System.out.println("Add vehicle");
                case "9" -> System.out.println("Remove vehicle");

                case "99" -> {
                    System.out.println("Thank you for visiting!");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void show(List<Vehicle> vehicles) {

        if (vehicles.isEmpty()) {
            System.out.println("No matching vehicles found.");
            return;
        }

        System.out.printf(
                "%-8s %-6s %-12s %-14s %-8s %-10s %10s %12s%n",
                "VIN", "YEAR", "MAKE", "MODEL",
                "TYPE", "COLOR", "MILES", "PRICE"
        );

        for (Vehicle vehicle : vehicles) {
            System.out.printf(
                    "%-8d %-6d %-12s %-14s %-8s %-10s %,10d $%,11.2f%n",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice()
            );
        }
    }

}
