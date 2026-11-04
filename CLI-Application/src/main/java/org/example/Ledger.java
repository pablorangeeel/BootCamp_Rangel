package org.example;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BoldGreen = "\u001B[1;92m";

    public static boolean askToContinue(Scanner scanner) {
        System.out.print("\u001B[32m");
        System.out.print("\nWould you like to make another transaction?");
        System.out.print("\nYES: Menu principal");
        System.out.print("\nNOT: Exit");
        System.out.print("\nEnter an option:   ");
        String answer = scanner.nextLine();
        System.out.print("\u001B[0m");

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("Redirecting");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print(".");
                System.out.flush();
            }
            System.out.println();
            return true;

        } else {
            return false;
        }
    }
    public static void showAllTransactions() {
        List<Details> transactions = FileManager.readTransactions();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        transactions.sort(Comparator.comparing(
                (Details transaction) ->
                        LocalDateTime.parse(
                                transaction.getDate().trim() + " " +
                                        transaction.getTime().trim(),
                                formatter
                        )
        ).reversed());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\n================== A L L   T R A N S A C T I O N S ==================");
        System.out.println("|    Date      |   Time   |    Description   |   Vendor   |   Amount  |");
        System.out.println("---------------------------------------------------------------------");
        for (Details transaction : transactions) {
            System.out.printf("| %s |", transaction.getDate());
            System.out.printf(" %s |", transaction.getTime());
            System.out.printf("%s |", transaction.getDescription());
            System.out.printf("%s |", transaction.getVendor());
            if (transaction.getAmount()<0) {
                System.out.printf(RED+" $%.2f "+RESET+"|%n", transaction.getAmount());
                System.out.println("---------------------------------------------------------------------");
            }
            else {
                System.out.printf(GREEN+"$%.2f "+RESET+"|%n", transaction.getAmount());
                System.out.println("---------------------------------------------------------------------");
            }

        }
    }
    public static void showDeposit() {
        List<Details> transactions = FileManager.readTransactions();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        transactions.sort(Comparator.comparing(
                (Details transaction) ->
                        LocalDateTime.parse(
                                transaction.getDate().trim() + " " +
                                        transaction.getTime().trim(),
                                formatter
                        )
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
                System.out.printf("Amount:"+GREEN + " $%.2f%n" + RESET,
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
        System.out.println("\n========== P A Y M E N T S ==========");
        for (Details transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("Date: %s%n", transaction.getDate());
                System.out.printf("Time: %s%n", transaction.getTime());
                System.out.printf("Description: %s%n", transaction.getDescription());
                System.out.printf("Vendor: %s%n", transaction.getVendor());
                System.out.printf("Amount:"+RED + "$%.2f%n" + RESET, transaction.getAmount());
                System.out.println("--------------------------------------");
            }
        }
    }
    public static void displayLedger() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print(PURPLE + "\n\n\n\n\n\n\t========== LEDGER============" + RESET);
            System.out.print(GREEN +"\n1. All transactions");
            System.out.print("\n2. Deposits");
            System.out.print("\n3. Payments");
            System.out.print("\n4. Reports");
            System.out.print("\n5. Back to main menu");
            System.out.print(BoldGreen + "\t\nPlease select and options: " + RESET);
            int r = scanner.nextInt();
            switch (r) {
                case 1:
                    //All transactions
                    showAllTransactions();
                    scanner.nextLine();
                    running = askToContinue(scanner);
                    break;
                case 2:
                    //Deposits only
                    showDeposit();
                    running = askToContinue(scanner);
                    break;
                case 3:
                    //payments
                    showPayments();
                    running = askToContinue(scanner);
                    break;
                case 4:
                    //reports
                    Reports.displayReports();
                    running = askToContinue(scanner);
                    break;
                case 5:
                    running = false;
                    break;
                default:

                    break;
            }
        }
    }
}
