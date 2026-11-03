package org.example;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String PURPLE = "\u001B[35m";
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        String CLEANTERMINAL = "\033[H\033[2J";
        while (true){

            System.out.printf(PURPLE+"\tWELCOME TO THE CLI PROGRAM\n"+ RESET);
            System.out.println(GREEN+"\t==========MENU==========");
            System.out.println("1) Add Deposit");
            System.out.println("2) Make a payment");
            System.out.println("3) Ledger");
            System.out.println("4) Exit \n"+RESET);
            int choice = scanner.nextInt();

            scanner.nextLine();
            switch (choice){
                case 1:
                    LocalDate date = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    String formatDate = formatter.format(date);

                    LocalTime time = LocalTime.now();
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
                    String formatTime = formatter1.format(time);

                    System.out.println("Enter description: ");
                    String description = scanner.nextLine();

                    System.out.println("Enter vendor: ");
                    String vendor = scanner.nextLine();

                    System.out.println("Enter deposit amount: $");
                    float amount = scanner.nextFloat();

                    Details deposit = new Details(formatDate, formatTime, description, vendor, amount);
                    FileManager.writeTransactions(deposit);
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");
                    break;
                case 4:
                    System.out.println(CLEANTERMINAL);
                    System.out.flush();
                    System.out.println("Thanks for visiting us!!");
                    System.out.println("BYE");
                    System.exit(0);
                default:
                    System.out.println("Please try again");
            }

        }
    }
}