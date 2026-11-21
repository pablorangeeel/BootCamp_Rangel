package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalesContract extends Contract {
    private static final BigDecimal PRICE_THRESHOLD = new BigDecimal("10000");
    private boolean financed;

    public SalesContract(String date, String customerName, String customerEmail,
                         Vehicle vehicle, boolean financed) {
        super(date, customerName, customerEmail, vehicle);
        this.financed = financed;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    public BigDecimal getSalesTaxAmount() {
        return getVehicle().getPrice().multiply(new BigDecimal("0.05"))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRecordingFee() {
        return new BigDecimal("100.00");
    }

    public BigDecimal getProcessingFee() {
        return qualifiesForLongerLoan() ? new BigDecimal("495.00") : new BigDecimal("295.00");
    }

    private boolean qualifiesForLongerLoan() {
        return getVehicle().getPrice().compareTo(PRICE_THRESHOLD) >= 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getVehicle().getPrice().add(getSalesTaxAmount())
                .add(getRecordingFee()).add(getProcessingFee())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        if (!financed) {
            return new BigDecimal("0.00");
        }

        if (qualifiesForLongerLoan()) {
            return calculatePayment(getTotalPrice(), 0.0425, 48);
        }

        return calculatePayment(getTotalPrice(), 0.0525, 24);
    }
}

