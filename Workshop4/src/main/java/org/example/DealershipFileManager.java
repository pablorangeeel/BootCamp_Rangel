
package org.example;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DealershipFileManager {

    private static final Path FILE = Path.of("src/main/resources/cars.csv");

    public static Dealership getDealership() {

        if (!Files.exists(FILE)) {
            Dealership dealership = new Dealership(
                    "D & B Used Cars",
                    "111 Old Benbrook Rd",
                    "817-555-5555"
            );

            saveDealership(dealership);
            return dealership;
        }

        try {
            List<String> lines = Files.readAllLines(FILE);

            if (lines.isEmpty()) {
                throw new IllegalStateException("Inventory file is empty.");
            }

            String[] info = lines.get(0).split("\\|", -1);

            if (info.length != 3) {
                throw new IllegalStateException("Invalid dealership information.");
            }

            Dealership dealership = new Dealership(
                    info[0], info[1], info[2]
            );

            for (int i = 1; i < lines.size(); i++) {

                if (lines.get(i).isBlank()) {
                    continue;
                }

                String[] parts = lines.get(i).split("\\|", -1);

                if (parts.length != 8) {
                    throw new IllegalStateException(
                            "Invalid vehicle on line " + (i + 1)
                    );
                }

                Vehicle vehicle = new Vehicle(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5],
                        Integer.parseInt(parts[6]),
                        new BigDecimal(parts[7])
                );

                dealership.addVehicle(vehicle);
            }

            return dealership;

        } catch (IOException | NumberFormatException e) {
            throw new IllegalStateException(
                    "Could not load inventory.csv", e
            );
        }
    }

    public static void saveDealership(Dealership dealership) {

        List<String> lines = new ArrayList<>();

        lines.add(
                dealership.getName() + "|" +
                        dealership.getAddress() + "|" +
                        dealership.getPhone()
        );

        for (Vehicle vehicle : dealership.getAllVehicles()) {

            String line = vehicle.getVin() + "|" +
                    vehicle.getYear() + "|" +
                    vehicle.getMake() + "|" +
                    vehicle.getModel() + "|" +
                    vehicle.getVehicleType() + "|" +
                    vehicle.getColor() + "|" +
                    vehicle.getOdometer() + "|" +
                    vehicle.getPrice().toPlainString();

            lines.add(line);
        }

        try {
            Files.write(FILE, lines);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Could not save inventory.csv", e
            );
        }
    }
}

