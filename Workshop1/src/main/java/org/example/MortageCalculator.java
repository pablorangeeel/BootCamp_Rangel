package org.example;

import java.util.Scanner;

public class MortageCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("This is a mortgage Calculator");
        System.out.println("Enter your total loan: ");
        int loanAmount = scanner.nextInt();

        System.out.println("Enter your interest (e. g for 7.623% enter 7.623)");
        float interest = scanner.nextFloat();

        System.out.println("Enter your loan length on years");
        float n = scanner.nextFloat();

        float numberOfMonthlyPayments;
        float monthlyPayments;
        float monthlyInterest = interest/100/12;
        numberOfMonthlyPayments = n * 12;
        monthlyPayments = (float) (loanAmount * monthlyInterest * Math.pow(1+monthlyInterest, numberOfMonthlyPayments) / (Math.pow((1+monthlyInterest),(numberOfMonthlyPayments-1))));

        //        float part1 = interest*(1 + interest);
//        double part11 = Math.pow(part1, numberOfMonthlyPayments);
//
//        float part2 =(1+interest);
//        double part22 = Math.pow(part2, numberOfMonthlyPayments)-1;
//        float part3 = (part11/part22);
//        monthlyPayments = loanAmount * part3;
        //[(1+interest)^numberOfMonthlyPayments-1];
        System.out.printf("Your monthly payments are: $%.3f%n",monthlyPayments );
        float  totalInterest = (monthlyPayments * numberOfMonthlyPayments)-loanAmount;
        System.out.printf("Your total interest is: $%.2f", totalInterest);

    }
}
