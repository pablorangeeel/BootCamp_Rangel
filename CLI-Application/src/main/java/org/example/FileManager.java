package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                String[] brokenUpRow = row.split("\\|");

                LocalDate date = LocalDate.parse(brokenUpRow[0]);
                LocalDateTime time = LocalDateTime.parse(brokenUpRow[1]);
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
            System.out.println("There's been a problem when reading the file");
        }
        return detailsList;
    }

}

