package fooddelivery.util;

import fooddelivery.model.Order;
import java.io.*;
import java.util.*;

public class FileHandler {

    public List<Order> loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists())
            throw new FileNotFoundException("File not found: " + filePath);
        if (!file.canRead())
            throw new IOException("Cannot read file: " + filePath);

        List<Order> orders     = new ArrayList<>();
        boolean     headerDone = false;
        int         lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) continue; // skip comments

                if (!headerDone) { headerDone = true; continue; }     // skip header row

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.err.printf("WARNING: Skipping malformed line %d: %s%n",
                            lineNumber, line);
                    continue;
                }

                try {
                    String orderId   = parts[0].trim();
                    String customer  = parts[1].trim();
                    String food      = parts[2].trim();
                    int    deadline  = Integer.parseInt(parts[3].trim());
                    double profit    = Double.parseDouble(parts[4].trim());
                    int    procTime  = (parts.length > 5) ? Integer.parseInt(parts[5].trim()) : 1;
                    String priority  = (parts.length > 6) ? parts[6].trim() : "MEDIUM";

                    orders.add(new Order(orderId, customer, food,
                            deadline, profit, procTime, priority));

                } catch (NumberFormatException e) {
                    System.err.printf("WARNING: Invalid number on line %d — skipped.%n", lineNumber);
                } catch (IllegalArgumentException e) {
                    System.err.printf("WARNING: Line %d — %s — skipped.%n",
                            lineNumber, e.getMessage());
                }
            }
        }

        if (orders.isEmpty())
            throw new IOException("No valid orders found in: " + filePath);

        System.out.printf("Loaded %d orders from: %s%n%n", orders.size(), filePath);
        return orders;
    }
}
