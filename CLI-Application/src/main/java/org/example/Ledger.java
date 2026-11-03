package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static void showAllTransactions() {
        List<Details> transactions = FileManager.readTransactions();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        transactions.sort(Comparator.comparing(
                (Details transaction) ->
                        LocalDate.parse(transaction.getDate(), formatter)
        ).reversed());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n========== ALL TRANSACTIONS ==========");

        for (Details transaction : transactions) {
            System.out.printf("Date: %s%n", transaction.getDate());
            System.out.printf("Time: %s%n", transaction.getTime());
            System.out.printf("Description: %s%n", transaction.getDescription());
            System.out.printf("Vendor: %s%n", transaction.getVendor());
            if (transaction.getAmount()<0) {
                System.out.printf(RED+"Amount: $%.2f%n"+RESET, transaction.getAmount());
            }
            else {
                System.out.printf(GREEN+"Amount: $%.2f%n"+RESET, transaction.getAmount());
            }
            System.out.println("--------------------------------------");
        }
    }
    public static void showDeposit() {

        List<Details> transactions = FileManager.readTransactions();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");

        transactions.sort(Comparator.comparing(
                (Details transaction) ->
                        LocalDate.parse(transaction.getDate(), formatter)
        ).reversed());

        boolean foundDeposit = false;

        System.out.println("\n========== D E P O S I T S ==========");

        for (Details transaction : transactions) {
            if (transaction.getAmount() > 0) {
                foundDeposit = true;
                System.out.printf("Date: %s%n", transaction.getDate());
                System.out.printf("Time: %s%n", transaction.getTime());
                System.out.printf("Description: %s%n", transaction.getDescription());
                System.out.printf("Vendor: %s%n", transaction.getVendor());
                System.out.printf(GREEN + "Amount: $%.2f%n" + RESET,
                        transaction.getAmount());
                System.out.println("----------------------------------");
            }
        }
        if (!foundDeposit) {
            System.out.println("No deposits found.");
        }
    }

    public static void showPayments() {

        List<Details> transactions = FileManager.readTransactions();
        System.out.println("\n========== D E P O S I T S ==========");
        for (Details transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("Date: %s%n", transaction.getDate());
                System.out.printf("Time: %s%n", transaction.getTime());
                System.out.printf("Description: %s%n", transaction.getDescription());
                System.out.printf("Vendor: %s%n", transaction.getVendor());
                System.out.printf(RED + "Amount: $%.2f%n" + RESET, transaction.getAmount());
                System.out.println("--------------------------------------");
            }
        }
    }

    public static void displayLedger() {
        Scanner scanner = new Scanner(System.in);

        String PURPLE = "\u001B[35m";
        String GREEN = "\u001B[32m";
        String BoldGreen = "\u001B[1;92m";
        String BLUE = "\u001B[36m";
        String BoldBlue = "\u001B[1;34m";
        String RESET = "\u001B[0m";
        String CLEANTERMINAL = "\033[H\033[2J";


        System.out.print(PURPLE + "\n\n\n\n\n\n\t========== LEDGER============" + RESET);
        System.out.print(GREEN + "\n\t1. All transactions");
        System.out.print("\t\n2. Deposits");
        System.out.print("\t\n3. Payments");
        System.out.print("\t\n4. Reports");
        System.out.print("\t\n5. Back to main menu");
        System.out.print(BoldGreen + "\t\nPlease select and options: " + RESET);
        int r = scanner.nextInt();

        switch (r) {
            case 1:
                //All transactions
                showAllTransactions();
                break;
            case 2:
                //Deposits only
                showDeposit();
                break;
            case 3:
                //payments
                showPayments();
                break;
            case 4:
                //reports

                break;
            case 5:
                //back to main menu
                break;
            default:
        }
    }
}
