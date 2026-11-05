
package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
public class Reports {
    // Terminal colors
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String FILE_NAME = "src/main/resources/Transactions.csv";
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private record Transaction(
            LocalDate date,
            String time,
            String description,
            String vendor,
            double amount
    ) {}
    public static boolean askToContinue(Scanner scanner) {
        scanner.nextLine();
        System.out.print("\u001B[1;36m");
        System.out.print("\n\tWould you like to see  another list of transactions?");
        System.out.print("\n\tYES: Menu principal");
        System.out.print("\n\tNOT: Exit");
        System.out.print("\n\tEnter an option:   ");
        scanner.nextLine();
        String answer = scanner.nextLine();
        System.out.print("\u001B[0m");

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("\u001B[1;35m\tR E D I R E C T I N G ");
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
    // REPORTS MENU
    public static void displayReports() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println(BLUE + "\n\t===================== R E P O R T S =====================" + RESET);
            System.out.println("\t1) Month to Date");
            System.out.println("\t2) Previous Month");
            System.out.println("\t3) Year to Date");
            System.out.println("\t4) Previous Year");
            System.out.println("\t5) Search by Vendor");
            System.out.println("\t6) Custom Search");
            System.out.println("\t0) Back to Ledger");
            System.out.print(GREEN + "\n\tPlease select an option: " + RESET);
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(RED + "\tPlease enter a valid number." + RESET);
                continue;
            }

            switch (choice) {
                case 1:
                    monthToDate();
                    running = askToContinue(scanner);
                    break;
                case 2:
                    previousMonth();
                    running = askToContinue(scanner);
                    break;
                case 3:
                    yearToDate();
                    running = askToContinue(scanner);
                    break;
                case 4:
                    previousYear();
                    running = askToContinue(scanner);
                    break;
                case 5:
                    searchByVendor(scanner);
                    running = askToContinue(scanner);
                    break;
                case 6:
                    customSearch(scanner);
                    running = askToContinue(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println(RED + "Invalid option. Try again." + RESET);
            }
        }
    }

    // READ TRANSACTIONS
    private static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try {
            Path path = Path.of(FILE_NAME);

            if (!Files.exists(path)) {
                System.out.println(RED + "Transaction file not found: "
                        + FILE_NAME + RESET);
                return transactions;
            }

            for (String line : Files.readAllLines(path)) {
                if (line.isBlank() ||
                        line.trim().toLowerCase().startsWith("date")) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length != 5) {
                    continue;
                }

                try {
                    transactions.add(new Transaction(
                            LocalDate.parse(parts[0].trim(), DATE_FORMAT),
                            parts[1].trim(),
                            parts[2].trim(),
                            parts[3].trim(),
                            Double.parseDouble(parts[4].trim())
                    ));
                } catch (RuntimeException e) {
                    System.out.println(RED
                            + "Skipping invalid transaction: "
                            + line + RESET);
                }
            }

        } catch (IOException e) {
            System.out.println(RED + "Error reading transactions: "
                    + e.getMessage() + RESET);
        }

        // Newest transactions first
        transactions.sort(
                Comparator.comparing(Transaction::date)
                        .thenComparing(Transaction::time)
                        .reversed()
        );

        return transactions;
    }

    // MONTH TO DATE
    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);

        showDateRange(" M O N T H   T O   D A T E ", start, today);
    }

    // PREVIOUS MONTH
    private static void previousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        LocalDate start = lastMonth.withDayOfMonth(1);
        LocalDate end = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth());

        showDateRange(" P R E V I O U S   M O N T H ", start, end);
    }

    // YEAR TO DATE
    private static void yearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfYear(1);

        showDateRange(" Y E A R   T O   D A T E ", start, today);
    }

    // PREVIOUS YEAR
    private static void previousYear() {
        LocalDate lastYear = LocalDate.now().minusYears(1);
        LocalDate start = lastYear.withDayOfYear(1);
        LocalDate end = lastYear.withDayOfYear(lastYear.lengthOfYear());

        showDateRange(" P R E V I O U S   Y E A R ", start, end);
    }

    // DATE RANGE FILTER
    private static void showDateRange(
            String title, LocalDate start, LocalDate end) {

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : readTransactions()) {
            LocalDate date = transaction.date();

            if (!date.isBefore(start) && !date.isAfter(end)) {
                results.add(transaction);
            }
        }

        displayTransactions(title, results);
    }

    // SEARCH BY VENDOR
    private static void searchByVendor(Scanner scanner) {
        System.out.print("\nEnter vendor name: ");
        String vendor = scanner.nextLine().trim();

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : readTransactions()) {
            if (transaction.vendor().toLowerCase()
                    .contains(vendor.toLowerCase())) {
                results.add(transaction);
            }
        }

        displayTransactions(" V E N D O R   S E A R C H ", results);
    }

    // CUSTOM SEARCH
    private static void customSearch(Scanner scanner) {
        System.out.println(BLUE + "\n================== C U S T O M   S E A R C H ==================" + RESET);
        System.out.println("Leave any field blank to skip that filter.");

        LocalDate startDate = readDate(scanner, "Start date (MM/dd/yyyy): ");
        LocalDate endDate = readDate(scanner, "End date (MM/dd/yyyy): ");

        if (startDate != null && endDate != null
                && startDate.isAfter(endDate)) {
            System.out.println(RED + "Start date must be before end date." + RESET);
            return;
        }

        System.out.print("Description: ");
        String description = scanner.nextLine().trim().toLowerCase();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        System.out.print("Amount (exact, leave blank for all): ");
        String amountInput = scanner.nextLine().trim();

        Double amount = null;

        if (!amountInput.isEmpty()) {
            try {
                amount = Double.parseDouble(amountInput);
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid amount." + RESET);
                return;
            }
        }

        List<Transaction> results = new ArrayList<>();

        for (Transaction transaction : readTransactions()) {

            if (startDate != null
                    && transaction.date().isBefore(startDate)) {
                continue;
            }

            if (endDate != null
                    && transaction.date().isAfter(endDate)) {
                continue;
            }

            if (!transaction.description().toLowerCase()
                    .contains(description)) {
                continue;
            }

            if (!transaction.vendor().toLowerCase().contains(vendor)) {
                continue;
            }

            if (amount != null
                    && Math.abs(transaction.amount() - amount) > 0.00001) {
                continue;
            }

            results.add(transaction);
        }

        displayTransactions("CUSTOM SEARCH RESULTS", results);
    }

    // READ OPTIONAL DATE
    private static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return null;
            }

            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (Exception e) {
                System.out.println(RED
                        + "Invalid date. Use yyyy-MM-dd." + RESET);
            }
        }
    }

    // DISPLAY TRANSACTIONS
    private static void displayTransactions(
            String title, List<Transaction> transactions) {

        System.out.println(BLUE + "\n\t========== " + title
                + " ==========" + RESET);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.printf("%-12s %-10s %-25s %-20s %12s%n",
                "Date", "Time", "Description", "Vendor", "Amount");

        System.out.println("-".repeat(85));

        double total = 0;

        for (Transaction transaction : transactions) {
            String color = transaction.amount() >= 0 ? GREEN : RED;

            System.out.printf(
                    "%-12s %-10s %-25.25s %-20.20s %s%12.2f%s%n",
                    transaction.date(),
                    transaction.time(),
                    transaction.description(),
                    transaction.vendor(),
                    color,
                    transaction.amount(),
                    RESET
            );

            total += transaction.amount();
        }

        System.out.println("-".repeat(85));

        String totalColor = total >= 0 ? GREEN : RED;

        System.out.printf("Transactions: %d%n", transactions.size());
        System.out.printf("Net Total: %s$%.2f%s%n",
                totalColor, total, RESET);
    }
}
