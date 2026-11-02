package org.example;

/*This is my object class where I declared the
variables needed for a transaction, such as the date,
time, amount, and other related information.*/

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Details {
    private LocalDate date;
    private LocalDateTime time;
    private String description;
    private String vendor;
    private float amount;

    public Details(LocalDate date, LocalDateTime time, String description, String vendor, float amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
