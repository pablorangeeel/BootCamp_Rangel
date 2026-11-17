package org.example;

import java.math.BigDecimal;
import java.time.Year;

public class Vehicle extends Assets {
    private String makeModel;
    private int year;
    private int odometer;

    public Vehicle(String description, String dateAcquired,
                   BigDecimal originalCost, String makeModel,
                   int year, int odometer) {
        super(description, dateAcquired, originalCost);
        this.makeModel = makeModel;
        this.year = year;
        this.odometer = odometer;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    @Override
    public BigDecimal getValue() {
        int age = Math.max(0, Year.now().getValue() - year);
        BigDecimal value;

        if (age > 10) {
            value = new BigDecimal("1000.00");
        } else {
            BigDecimal yearlyRate;

            if (age <= 3) {
                yearlyRate = new BigDecimal("0.03");
            } else if (age <= 6) {
                yearlyRate = new BigDecimal("0.06");
            } else {
                yearlyRate = new BigDecimal("0.08");
            }

            BigDecimal depreciation = getOriginalCost()
                    .multiply(yearlyRate)
                    .multiply(BigDecimal.valueOf(age));

            value = getOriginalCost().subtract(depreciation);
        }

        String normalizedMakeModel = makeModel.toLowerCase(
                java.util.Locale.ROOT
        );

        boolean isHondaOrToyota = normalizedMakeModel.contains("honda")
                || normalizedMakeModel.contains("toyota");

        if (odometer > 100000 && !isHondaOrToyota) {
            value = value.multiply(new BigDecimal("0.75"));
        }

        return value;
    }
}
