package org.example;

/*This is my object class where I declared the
variables needed for a transaction, such as the date,
time, amount, and other related information.*/

public class Details {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private float amount;

    public Details(String date, String time, String description, String vendor, float amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    public String getDate() {
        return date;
    }

    public String getTime() {return time;}

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public float getAmount() {
        return amount;
    }

}
