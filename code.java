//Data pre-processing: Cleaning, sorting, and transforming data to remove errors and inconsistencies.


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataPreProcessing {

    public static void main(String[] args) {
        String inputFile = "transactions.csv";
        String outputFile = "transactions_cleaned.csv";
        List<String[]> data = readData(inputFile);
        List<String[]> cleanedData = cleanData(data);
        writeData(outputFile, cleanedData);
    }

    public static List<String[]> readData(String inputFile) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String[]> cleanData(List<String[]> data) {
        List<String[]> cleanedData = new ArrayList<>();
        for (String[] record : data) {
            if (record.length != 5) { // Check for missing or extra fields
                continue;
            }
            String transactionType = record[0].trim().toLowerCase();
            double amount;
            try {
                amount = Double.parseDouble(record[1].trim());
            } catch (NumberFormatException e) {
                continue; // Skip invalid amount
            }
            String accountNumber = record[2].trim();
            String date = record[3].trim();
            String time = record[4].trim();
            String[] cleanedRecord = new String[] { transactionType, String.valueOf(amount), accountNumber, date, time };
            cleanedData.add(cleanedRecord);
        }
        return cleanedData;
    }

    public static void writeData(String outputFile, List<String[]> data) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (String[] record : data) {
                String line = String.join(",", record);
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
