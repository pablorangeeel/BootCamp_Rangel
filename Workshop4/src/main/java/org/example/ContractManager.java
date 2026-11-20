package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContractManager {

    public static void readContracts (File contractsFile) {
        List<Contract> ContractList = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("src/main/resources/contracts.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row;

            while ((row = bufferedReader.readLine()) !=null){
                String [] breakUpRow = row.split("\\|");
                int id = Integer.parseInt(breakUpRow[0]);
                String name = breakUpRow[1];
                double price = Double.parseDouble(breakUpRow[2]);

                Contract contract = new Contract();
                productsList.add(product);
            }
            bufferedReader.close();
        }
        catch (IOException ex){
            System.out.println("Could not read file, check file");
        }
        catch (Exception ex){
            System.out.println("Could not read, check format");
        }
    }
    public static void writeProduct (Product product){
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/inventory.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String line = String.format("%d, %s, %.2f", product.getId(), product.getName(), product.getPrice());

            bufferedWriter.newLine();
            bufferedWriter.write(line);

            bufferedWriter.close();

            System.out.println("Your product have been safe on the data base successfully");
        }
        catch (IOException ex){
            System.out.println("Could not write the product on data base");
        }
        catch (Exception ex){
            System.out.println("Error when handling file");
        }
    }
}
}
