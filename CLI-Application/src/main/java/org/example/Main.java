package org.example;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String PURPLE = "\u001B[35m";Started my application by creating the necessary objects, data files, and file reader components. I also developed the main menu structure, which will allow me to begin implementing and testing each menu option individually.
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

            System.out.print(CLEANTERMINAL);
            System.out.flush();
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("HELLO");
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