package org.example;

import java.util.Scanner;

public class Calculator2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your deposit amount: ");
        float depositAmount = scanner.nextFloat();

        System.out.println("Enter the annual rate as a percent (e. g. 7.625 for 7.625%)");
        float annualInterest = scanner.nextFloat();
        float annualInterestRate = annualInterest/100;

        System.out.println("Enter the number of years: ");
        int years = scanner.nextInt();
        int daysYear = 365;
        double daysInYears = years * daysYear;

        float endingValance = (float) (depositAmount * Math.pow((1 + (annualInterestRate/daysYear)),daysInYears ));

        System.out.printf("Your CD ending balance in %d years will be $%.2f%n", years, endingValance );
        float interest = endingValance- depositAmount;
        System.out.printf("You would have earn $%.2f in interest", interest);
    }
}
