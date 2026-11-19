package org.example;

import java.math.BigDecimal;
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

                case "1" -> {
                    try {
                        System.out.print("Minimum price: ");
                        BigDecimal min = new BigDecimal(scanner.nextLine().trim());

                        System.out.print("Maximum price: ");
                        BigDecimal max = new BigDecimal(scanner.nextLine().trim());

                        show(dealership.getVehiclesByPrice(min, max));
                    } catch (NumberFormatException e) {
                        System.out.println("Enter prices like 5000 or 15000.50.");
                    }
                }

                case "2" -> {
                    System.out.print("Enter the make: ");
                    String make = scanner.nextLine().trim();

                    System.out.print("Enter the model: ");
                    String model = scanner.nextLine().trim();

                    show(dealership.getVehiclesByMakeModel(make, model));
                }

                case "3" -> {
                    try {
                        System.out.print("Earliest year: ");
                        int min = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Latest year: ");
                        int max = Integer.parseInt(scanner.nextLine().trim());

                        show(dealership.getVehiclesByYear(min, max));
                    } catch (NumberFormatException e) {
                        System.out.println("Enter whole years like 2015 and 2023.");
                    }
                }

                case "4" -> {
                    System.out.print("Enter the color: ");
                    String color = scanner.nextLine().trim();

                    show(dealership.getVehiclesByColor(color));
                }

                case "5" -> {
                    try {
                        System.out.print("Minimum mileage: ");
                        int min = Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Maximum mileage: ");
                        int max = Integer.parseInt(scanner.nextLine().trim());

                        show(dealership.getVehiclesByMileage(min, max));
                    } catch (NumberFormatException e) {
                        System.out.println("Enter whole numbers without commas, like 50000.");
                    }
                }

                case "6" -> {
                    System.out.print("Enter the vehicle type: ");
                    String type = scanner.nextLine().trim();

                    show(dealership.getVehiclesByType(type));
                }

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
