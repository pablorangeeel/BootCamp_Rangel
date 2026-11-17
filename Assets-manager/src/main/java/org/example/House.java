package org.example;

import java.math.BigDecimal;

public class House extends Assets {
    private String address;
    private int condition;
    private int squareFoot;
    private int lotSize;

    public House(String description, String dateAcquired,
                 BigDecimal originalCost, String address,
                 int condition, int squareFoot, int lotSize) {
        super(description, dateAcquired, originalCost);
        this.address = address;
        this.condition = condition;
        this.squareFoot = squareFoot;
        this.lotSize = lotSize;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public int getSquareFoot() {
        return squareFoot;
    }

    public void setSquareFoot(int squareFoot) {
        this.squareFoot = squareFoot;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    @Override
    public BigDecimal getValue() {
        int pricePerSquareFoot = switch (condition) {
            case 1 -> 180;
            case 2 -> 130;
            case 3 -> 90;
            case 4 -> 80;
            default -> throw new IllegalArgumentException(
                    "Condition must be between 1 and 4."
            );
        };

        BigDecimal houseValue = BigDecimal.valueOf(squareFoot)
                .multiply(BigDecimal.valueOf(pricePerSquareFoot));

        BigDecimal landValue = BigDecimal.valueOf(lotSize)
                .multiply(new BigDecimal("0.25"));

        return houseValue.add(landValue);
    }
}
