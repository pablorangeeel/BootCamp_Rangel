package org.example;

import java.util.Scanner;

public class Calculator3 {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        System.out.println("Enter the monthly payout in dollars");
        float monthlyPayout = scanner.nextFloat();

        System.out.println("Enter the expected annual interest rate in percent (e. g. 7.625 for 7.625%)");
        float expectedInterest = scanner.nextFloat();
        double monthlyInterest = expectedInterest/ 100.0 / 12.0 ;

        System.out.println("Enter the number of years the annuity pays out");
        int years = scanner.nextInt();
        int yearsInMonths = years * 12;

        double moneyNeeded;
        if (monthlyInterest == 0){
            moneyNeeded= monthlyPayout * yearsInMonths;
        }else {
            moneyNeeded = monthlyPayout *( 1 - Math.pow(1 + monthlyInterest, - yearsInMonths))/monthlyInterest;
        }
        System.out.printf("The amount of money tiy need today is %.2f%n", moneyNeeded);
        System.out.printf("Monthly interest you are earning is %f", monthlyInterest);

    }
}
