package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseContract extends Contract {

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    public BigDecimal getExpectedEndingValue() {
        return getVehicle().getPrice().multiply(new BigDecimal("0.50"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLeaseFee() {
        return getVehicle().getPrice().multiply(new BigDecimal("0.07"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getExpectedEndingValue().add(getLeaseFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return calculatePayment(getTotalPrice(), 0.04, 36);
    }
}
