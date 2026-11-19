package org.example;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Assets> assets = new ArrayList<>();

        assets.add(new House(
                "My house",
                "2020-06-15",
                new BigDecimal("250000"),
                "123 Main St, Charlotte, NC",
                2,
                1800,
                6000
        ));

        assets.add(new House(
                "My vacation home",
                "2023-08-10",
                new BigDecimal("180000"),
                "456 Beach Rd, Wilmington, NC",
                1,
                1200,
                4000
        ));

        assets.add(new Vehicle(
                "My car",
                "2021-03-20",
                new BigDecimal("18000"),
                "Subaru Legacy",
                2016,
                120000
        ));

        assets.add(new Vehicle(
                "Tom's truck",
                "2024-01-12",
                new BigDecimal("35000"),
                "Toyota Tacoma",
                2022,
                110000
        ));

        for (Assets asset : assets) {
            System.out.println("--------------------------------");
            System.out.println("Description: " + asset.getDescription());
            System.out.println("Date acquired: " + asset.getDateAcquired());
            System.out.printf("Original cost: $%,.2f%n",
                    asset.getOriginalCost());
            System.out.printf("Current value: $%,.2f%n",
                    asset.getValue());

            if (asset instanceof House) {
                House house = (House) asset;
                System.out.println("Address: " + house.getAddress());
            } else if (asset instanceof Vehicle) {
                Vehicle vehicle = (Vehicle) asset;
                System.out.println("Year: " + vehicle.getYear());
                System.out.println("Make/model: " + vehicle.getMakeModel());
            }

            System.out.println();
        }
    }
}