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
    public static final String BoldPURPLE = "\u001B[1;35m";
    public static final String BoldGreen = "\u001B[1;92m";

    public static boolean askToContinue(Scanner scanner) {
        scanner.nextLine();
        System.out.print("\u001B[1;36m");
        System.out.print("\n\tWould you like to see  another list of transactions?");
        System.out.print("\n\tYES: Menu principal");
        System.out.print("\n\tNOT: Exit");
        System.out.print("\n\tEnter an option:   ");
        String answer = scanner.nextLine();
        System.out.print("\u001B[0m");

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print(BoldPURPLE+"\tR E D I R E C T I N G ");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("."+RESET);
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
        System.out.println("\n==================== D E P O S I T S =====================");
        for (Details transaction : transactions) {
            if (transaction.getAmount() > 0) {
                foundDeposit = true;
                System.out.printf("\tDate: %s%n", transaction.getDate());
                System.out.printf("\tTime: %s%n", transaction.getTime());
                System.out.printf("\tDescription: %s%n", transaction.getDescription());
                System.out.printf("\tVendor: %s%n", transaction.getVendor());
                System.out.printf("\tAmount:"+GREEN + " $%.2f%n" + RESET,
                        transaction.getAmount());
                System.out.println("\t----------------------------------");
            }
        }
        if (!foundDeposit) {
            System.out.println("No deposits found.");
        }
    }
    public static void showPayments() {
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

        System.out.println("\n\t==================== P A Y M E N T S ======================");
        for (Details transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.printf("\tDate: %s%n", transaction.getDate());
                System.out.printf("\tTime: %s%n", transaction.getTime());
                System.out.printf("\tDescription: %s%n", transaction.getDescription());
                System.out.printf("\tVendor: %s%n", transaction.getVendor());
                System.out.printf("\tAmount:"+RED + "$%.2f%n" + RESET, transaction.getAmount());
                System.out.println("\t--------------------------------------");
            }
        }
    }
    public static void displayLedger() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print(PURPLE + "\n\n\n\n\n\n\t\t============== L E D G E R ================" + RESET);
            System.out.print(GREEN +"\n\t1. All transactions");
            System.out.print("\n\t2. Deposits");
            System.out.print("\n\t3. Payments");
            System.out.print("\n\t4. Reports");
            System.out.print("\n\t5. Back to main menu");
            System.out.print(BoldGreen + "\n\tPlease select and options: " + RESET);
            int r = scanner.nextInt();
            switch (r) {
                case 1:
                    //All transactions
                    showAllTransactions();
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