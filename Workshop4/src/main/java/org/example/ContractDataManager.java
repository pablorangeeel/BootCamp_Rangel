package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ContractDataManager {
    private static final File CONTRACTS_FILE = new File("src/main/resources/contracts.csv");

    public static void saveContract(Contract contract) throws IOException {
        saveContract(CONTRACTS_FILE, contract);
    }

    public static void saveContract(File file, Contract contract) throws IOException {
        Vehicle vehicle = contract.getVehicle();
        String shared = String.join("|",
                textField(contract.getDate()),
                textField(contract.getCustomerName()),
                textField(contract.getCustomerEmail()),
                String.valueOf(vehicle.getVin()),
                String.valueOf(vehicle.getYear()),
                textField(vehicle.getMake()),
                textField(vehicle.getModel()),
                textField(vehicle.getVehicleType()),
                textField(vehicle.getColor()),
                String.valueOf(vehicle.getOdometer()),
                vehicle.getPrice().toPlainString());

        String row;
        if (contract instanceof SalesContract) {
            SalesContract sale = (SalesContract) contract;
            row = String.join("|", "SALE", shared,
                    sale.getSalesTaxAmount().toPlainString(),
                    sale.getRecordingFee().toPlainString(),
                    sale.getProcessingFee().toPlainString(),
                    sale.getTotalPrice().toPlainString(),
                    sale.isFinanced() ? "YES" : "NO",
                    sale.getMonthlyPayment().toPlainString());
        } else if (contract instanceof LeaseContract) {
            LeaseContract lease = (LeaseContract) contract;
            row = String.join("|", "LEASE", shared,
                    lease.getExpectedEndingValue().toPlainString(),
                    lease.getLeaseFee().toPlainString(),
                    lease.getTotalPrice().toPlainString(),
                    lease.getMonthlyPayment().toPlainString());
        } else {
            throw new IllegalArgumentException("Unsupported contract type");
        }

        boolean needsNewline = false;
        if (file.exists() && file.length() > 0) {
            try (RandomAccessFile existing = new RandomAccessFile(file, "r")) {
                existing.seek(existing.length() - 1);
                needsNewline = existing.read() != '\n';
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            if (needsNewline) {
                writer.newLine();
            }
            writer.write(row);
            writer.newLine();
        }
    }

    private static String textField(String value) throws IOException {
        if (value == null || value.contains("|") || value.contains("\r") || value.contains("\n")) {
            throw new IOException("Contract fields cannot contain pipes or line breaks.");
        }
        return value;
    }

    public static List<Contract> readContracts(File file) throws IOException {
        List<Contract> contracts = new ArrayList<>();
        if (Files.notExists(file.toPath())) {
            return contracts;
        }

        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String row;
            int lineNumber = 0;
            while ((row = reader.readLine()) != null) {
                lineNumber++;
                if (row.isBlank()) {
                    continue;
                }

                String[] fields = row.split("\\|", -1);
                boolean sale = fields[0].equals("SALE") && fields.length == 18;
                boolean lease = fields[0].equals("LEASE") && fields.length == 16;
                if (!sale && !lease) {
                    throw new IOException("Invalid contract format on line " + lineNumber);
                }

                Vehicle vehicle;
                try {
                    vehicle = new Vehicle(Integer.parseInt(fields[4]), Integer.parseInt(fields[5]),
                            fields[6], fields[7], fields[8], fields[9],
                            Integer.parseInt(fields[10]), new BigDecimal(fields[11]));
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid vehicle data on line " + lineNumber, e);
                }

                if (sale) {
                    if (!fields[16].equals("YES") && !fields[16].equals("NO")) {
                        throw new IOException("Invalid finance option on line " + lineNumber);
                    }
                    contracts.add(new SalesContract(fields[1], fields[2], fields[3],
                            vehicle, fields[16].equals("YES")));
                } else {
                    contracts.add(new LeaseContract(fields[1], fields[2], fields[3], vehicle));
                }
            }
        }
        return contracts;
    }
}
