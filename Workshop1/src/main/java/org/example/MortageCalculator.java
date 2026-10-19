package org.example;

import java.util.Scanner;

public class MortageCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        float numberOfMonthlyPayments;
        double monthlyPayments = 0;

        System.out.println("This is a mortgage Calculator");
        System.out.println("Enter your total loan: ");
        int loanAmount = scanner.nextInt();

        System.out.println("Enter your interest (e. g for 7.623% enter 7.623)");
        float interest = scanner.nextFloat();

        System.out.println("Enter your loan length on years");
        float n = scanner.nextFloat();


        double monthlyInterest = interest / 100.0 / 12.0;
        numberOfMonthlyPayments = n * 12;

        double factor = Math.pow(1+  monthlyInterest, numberOfMonthlyPayments);
        if (monthlyInterest == 0){
            monthlyPayments = (double) loanAmount / numberOfMonthlyPayments;
        } else {
            monthlyPayments = loanAmount * monthlyInterest * factor/ (factor - 1);
        }
        System.out.printf("Your monthly payments are: $%.2f%n",monthlyPayments );

        double  totalInterest = (monthlyPayments * numberOfMonthlyPayments)-loanAmount;

        System.out.printf("Your total interest is: $%.2f", totalInterest);

    }
}
