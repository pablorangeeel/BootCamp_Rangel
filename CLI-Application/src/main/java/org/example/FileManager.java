package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    public static List<Details> readTransactions() {
        List<Details> detailsList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/Transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row;

            while ((row = bufferedReader.readLine()) != null) {
                if (row.startsWith("Date")) {
                    continue;
                }
                String[] brokenUpRow = row.split("\\|");

                String date = brokenUpRow[0];
                String time = brokenUpRow[1];
                String description = brokenUpRow[2];
                String vendor = brokenUpRow[3];
                float amount = Float.parseFloat(brokenUpRow[4]);


                Details transactions = new Details(date, time, description, vendor, amount );
               detailsList.add(transactions);

            }
            bufferedReader.close();
        } catch (IOException ex) {
            System.out.println("could not read the file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return detailsList;
    }
public static void writeTransactions(Details transactions){
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%s | %s | %s | %s | %.2f",
                    transactions.getDate(),
                    transactions.getTime(),
                    transactions.getDescription(),
                    transactions.getVendor(),
                    transactions.getAmount());

            bufferedWriter.write(line);
            bufferedWriter.newLine();

            bufferedWriter.close();
            System.out.println("\u001B[1;32m");
            System.out.println("The transaction has been register successfully");
            System.out.println("\u001B[0m");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception ex){
            System.out.println("There has been an error while writing the file");
        }
}
}

