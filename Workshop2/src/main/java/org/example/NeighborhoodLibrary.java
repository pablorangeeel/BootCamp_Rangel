package org.example;

import java.util.Scanner;

public class NeighborhoodLibrary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] books = {
                new Book(1, "9780134685991", "Effective Java", false, ""),
                new Book(2, "9780596009205", "Head First Java", true, "John Smith"),
                new Book(3, "9781617294945", "Java Concurrency in Practice", false, ""),
                new Book(4, "9781492056270", "Modern Java in Action", true, "Maria Garcia"),
                new Book(5, "9780135166307", "Core Java Volume I", false, ""),
                new Book(6, "9780134177304", "Core Java Volume II", false, ""),
                new Book(7, "9781260440232", "Java A Beginner's Guide", true, "David Wilson"),
                new Book(8, "9781119619130", "Beginning Programming with Java", false, ""),
                new Book(9, "9781801070242", "Java Coding Problems", false, ""),
                new Book(10, "9781484273913", "Learn Java the Easy Way", true, "Sarah Johnson"),
                new Book(11, "9781617293566", "Spring in Action", false, ""),
                new Book(12, "9781492072508", "Java Performance", false, ""),
                new Book(13, "9781617297571", "Grokking Algorithms", true, "Michael Brown"),
                new Book(14, "9780132350884", "Clean Code", false, ""),
                new Book(15, "9780201633610", "Design Patterns", true, "Emily Davis"),
                new Book(16, "9780134494166", "Clean Architecture", false, ""),
                new Book(17, "9781491950357", "Building Microservices", false, ""),
                new Book(18, "9781449331818", "Learning Java", true, "Carlos Martinez"),
                new Book(19, "9780596529321", "Java Cookbook", false, ""),
                new Book(20, "9780131872486", "Thinking in Java", true, "Lisa Anderson")
        };

        while (true) {
            System.out.printf("%n\t=====\t BOOK STORE HOME SCREEN \t===\t%n%n");
            System.out.println("\t\t(1)\t Show Available Books");
            System.out.println("\t\t(2)\t Show Checked Out Books");
            System.out.printf("\t\t(3)\t Exit%n%n");

            System.out.print("Enter your choice: ");
            int userInput = scanner.nextInt();


            switch (userInput) {
                case 1:
                    System.out.printf("BOOK ID \t\t TITLE \t\t\t\t BOOK ISNB%n");
                    for (int i = 0; i < books.length; i++) {
                        if (!books[i].isCheckedOut()) {
                            System.out.printf("%s\t%s\t\t\t\t%s%n", books[i].getId(), books[i].getTitle(), books[i].getIsnb());
                        }
                    }
                    System.out.println("\nDo you want to barrow a book?: YES/NOT");
                    scanner.nextLine();
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("yes")) {
                        System.out.println("\nEnter your Name: ");
                        String name = scanner.nextLine();
                        System.out.printf("Select a book from the list %n e. g. 1 effective java select '1' ");
                        int selectedBook = scanner.nextInt();
                        for (int j = 0; j < books.length; j++) {
                            if (books[j].getId() == selectedBook) {
                                if (!books[j].isCheckedOut()) {
                                    books[j].checkedOut(name);
                                    System.out.println("Book checked out successfully! ");
                                } else {
                                    System.out.println("This book is already checked out.");
                                }
                            }
                        }
                        break;

                    } else {
                        System.out.println("\n\n\n\n\n\n");
                            System.out.println("Returning to the home screen...");
                    }
                    break;
                case 2:
                    System.out.printf("BOOK ID \t\t TITLE \t\tChecked out to: %n");
                    for (int i = 0; i < books.length; i++) {
                        if (books[i].isCheckedOut()) {
                            System.out.printf("%s\t%s\t\t\t%s%n", books[i].getId(), books[i].getTitle(), books[i].getCheckedOutTo());
                        }
                    }
                    System.out.println("\nC - Check in a book");
                    System.out.println("X - Return to home screen");
                    System.out.print("Enter your choice: ");
                    scanner.nextLine();
                    String choice = scanner.nextLine();

                    if (choice.equalsIgnoreCase("C")) {
                        System.out.print("Enter the ID of the book to check in: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();

                        boolean found = false;

                        for (int i = 0; i < books.length; i++) {
                            if (books[i].getId() == bookId) {
                                found = true;

                                if (books[i].isCheckedOut()) {
                                    books[i].checkedIn();
                                    System.out.println("Book checked in successfully!");
                                } else {
                                    System.out.println("This book is not checked out.");
                                    System.out.print("\n\n\n\n\n\n");
                                }

                                break;
                            }
                        }

                        if (!found) {
                            System.out.println("Book ID not found.");
                            System.out.print("\n\n\n\n\n");
                        }
                    } else if (choice.equalsIgnoreCase("X")) {
                        System.out.println("Returning to the home screen...");
                    } else {
                        System.out.println("Invalid choice. Please enter C or X.");
                    }
                    break;
                case 3:
                    System.out.print("\n\n\n\n");
                    System.out.println("========================================");
                    System.out.println("\tThanks for visiting us");
                    System.out.println("========================================");
                    System.exit(0);
                default:
                    System.out.println("Please try again");
            }
        }
    }
}