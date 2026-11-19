package org.example;
import java.math.BigDecimal;
public class Vehicle {
    private int vin;
    private String model;
    private String color;
    private int year;
    private String make;
    private String vehicleType;
    private int odometer;
    private BigDecimal price;


    public Vehicle(int vin, int year, String make,
                   String model, String vehicleType,
                   String color, int odometer,
                   BigDecimal price) {

        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.odometer = odometer;
        this.price = price;
    }

    public int getVin() {
        return vin;
    }


    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYear() {
        return year;
    }


    public String getMake() {
        return make;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public int getOdometer() {
        return odometer;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
