package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            System.out.println("10 - Sell or lease a vehicle");
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
                case "10" -> sellOrLeaseVehicle();
                case "99" -> {
                    System.out.println("Thank you for visiting!");
                    running = false;
                }

                default -> System.out.println("Invalid option.");
            }
        }
    }
    private void sellOrLeaseVehicle() {
        System.out.print("Enter the vehicle VIN: ");
        int vin;
        try {
            vin = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Enter a numeric VIN from the inventory list.");
            return;
        }

        Vehicle selectedVehicle = null;
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                selectedVehicle = vehicle;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("No vehicle found with that VIN.");
            return;
        }

        System.out.print("Enter SALE or LEASE: ");
        String type = scanner.nextLine().trim();
        boolean isSale = type.equalsIgnoreCase("SALE");
        boolean isLease = type.equalsIgnoreCase("LEASE");

        if (!isSale && !isLease) {
            System.out.println("Enter SALE or LEASE.");
            return;
        }

        LocalDate today = LocalDate.now();
        if (isLease && today.getYear() - selectedVehicle.getYear() > 3) {
            System.out.println("Vehicles more than three model years old cannot be leased.");
            return;
        }

        System.out.print("Customer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Customer email: ");
        String email = scanner.nextLine().trim();
        if (name.isEmpty() || email.isEmpty()) {
            System.out.println("Enter both a customer name and email.");
            return;
        }

        String date = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        Contract contract;

        if (isSale) {
            System.out.print("Finance this sale? (YES/NO): ");
            String answer = scanner.nextLine().trim();
            boolean financed = answer.equalsIgnoreCase("YES");
            if (!financed && !answer.equalsIgnoreCase("NO")) {
                System.out.println("Enter YES or NO.");
                return;
            }
            contract = new SalesContract(date, name, email, selectedVehicle, financed);
        } else {
            contract = new LeaseContract(date, name, email, selectedVehicle);
        }

        System.out.printf("Total: $%.2f%n", contract.getTotalPrice());
        System.out.printf("Monthly payment: $%.2f%n", contract.getMonthlyPayment());
        System.out.print("Save this contract? (YES/NO): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("YES")) {
            System.out.println("Contract canceled.");
            return;
        }

        try {
            ContractDataManager.saveContract(contract);
        } catch (IOException e) {
            System.out.println("Could not save the contract: " + e.getMessage());
            return;
        }

        dealership.removeVehicle(selectedVehicle);
        try {
            DealershipFileManager.saveDealership(dealership);
            System.out.println("Contract saved and vehicle removed from inventory.");
        } catch (IllegalStateException e) {
            System.out.println("Contract was saved, but cars.csv could not be updated.");
            System.out.println("Retry saving the inventory or reconcile VIN " + vin
                    + " in cars.csv before restarting. Do not record the contract again.");
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
