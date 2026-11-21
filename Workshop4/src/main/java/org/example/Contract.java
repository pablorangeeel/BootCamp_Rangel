package org.example;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Contract {
    private String date;
    private String customerName;
    private String customerEmail;
    private Vehicle vehicle;

    protected Contract(String date, String customerName, String customerEmail, Vehicle vehicle) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicle = vehicle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public abstract BigDecimal getTotalPrice();

    public abstract BigDecimal getMonthlyPayment();

    protected static BigDecimal calculatePayment(BigDecimal total, double annualRate, int months) {
        double monthlyRate = annualRate / 12; //it assigns the value of divide yearly rate  by 12 to the monthly rate variable
        double payment = total.doubleValue() * monthlyRate
                / (1 - Math.pow(1 + monthlyRate, -months)); //This is the math with the price of the car to calculate the monthly payments.
        return BigDecimal.valueOf(payment).setScale(2, RoundingMode.HALF_UP);
    }
}

