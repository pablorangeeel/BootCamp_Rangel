package org.example;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Main {
    public static boolean askToContinue(Scanner scanner) {
        System.out.print("\u001B[32m");
        System.out.print("\n\t\tWould you like to make another transaction?");
        System.out.print("\n\tYES: Menu principal");
        System.out.print("\n\tNOT: Exit");
        System.out.print("\n\tEnter an option:   ");
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
                System.out.print(".");
                System.out.flush();
            }
            System.out.println("\u001B[0m");
            return true;

        } else {
            return false;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String PURPLE = "\u001B[35m";
        String GREEN = "\u001B[32m";
        String BoldGreen = "\u001B[1;92m";
        String BLUE = "\u001B[36m";
        String BoldBlue = "\u001B[1;34m";
        String RESET = "\u001B[0m";
        String CLEANTERMINAL = "\033[H\033[2J";
        System.out.println();


        System.out.print(PURPLE + "\n\t==================================================\n");
        System.out.print("\tW E L C O M E   T O  T H E   B a n k i n g  A p p \n");
        System.out.print("\t==================================================\n"+ RESET);
        System.out.print("\tThis application allows you to manage\n");
        System.out.print("\tdeposit records and save them to a CSV file.\n");
        System.out.println();
        System.out.print("\tSelect one of the menu options below\n");
        System.out.print("\tto begin using the application.\n");
        System.out.print(PURPLE+"\t================================================="+ RESET);

        boolean running = true;
        while (running) {
            System.out.print(PURPLE+ "\n\t=============== M A I N  M E N U ================");
            System.out.println();
            System.out.print(GREEN+"\n\t1) Add Deposit");
            System.out.print("\n\t2) Make a payment");
            System.out.print("\n\t3) Ledger");
            System.out.print("\n\t4) Exit \n");
            System.out.print(BoldGreen + "\tPlease select one option: " + RESET);
            String input = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            LocalDate date = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formatDate = formatter.format(date);
            //This part takes the build date feature and put it into the String variable I created
            //It also save it with the format  gave it

            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
            String formatTime = formatter1.format(time);
            switch (choice) {
                case 1:
                    //Add deposit
                    System.out.print(BLUE + "\n\n\n\n\t========= A D D  D E P O S I T =========\n" + RESET);

                    System.out.print(BLUE+"\n\tEnter description: ");
                    String description = scanner.nextLine();

                    System.out.print("\n\tEnter vendor: ");
                    String vendor = scanner.nextLine();

                    System.out.print("\n\tEnter deposit amount:  $"+RESET);
                    float amount = scanner.nextFloat();

                    Details deposit = new Details(formatDate, formatTime, description, vendor, amount);
                    FileManager.writeTransactions(deposit);
                    scanner.nextLine();
                    running = askToContinue(scanner);

                    break;
                case 2:
                    //Make a payment
                    System.out.print(BLUE + "\t==MAKE A PAYMENT==\n" + RESET);
                    System.out.print("\nEnter description: ");
                    String description1 = scanner.nextLine();

                    System.out.print("\nEnter vendor: ");
                    String vendor1 = scanner.nextLine();

                    System.out.print("\nEnter payment amount:  $ ");
                    float amount1 = scanner.nextFloat();

                    Details payment = new Details(formatDate, formatTime, description1, vendor1, -amount1);
                    FileManager.writeTransactions(payment);
                    scanner.nextLine();
                   running = askToContinue(scanner);
                    break;
                case 3:
                    //Ledger
                    Ledger.displayLedger();
                    running = askToContinue(scanner);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Please try again");
                    break;
            }
        }

        System.out.println(BLUE+"\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\t\t\tLogging out");
        Thread.sleep(700);
        System.out.print("\t\t\t\t\t\t.");
        Thread.sleep(500);
        System.out.print("\t.");
        Thread.sleep(500);
        System.out.println("\t.");
        Thread.sleep(1000);
        System.out.println("\n\t\t\t======================================");
        Thread.sleep(500);
        System.out.println("\t\t\t    THANKS FOR VISITING US!");
        Thread.sleep(1000);
        System.out.println("\t\t\t       SEE YOU SOON!");
        Thread.sleep(1000);
        System.out.println("\t\t\t======================================");
        Thread.sleep(700);
        System.out.println("\n\t\t\t\t\tGoodbye! 👋"+RESET);
    }
}