package fooddelivery.output;

import fooddelivery.algorithm.SchedulingResult;
import fooddelivery.model.Order;
import fooddelivery.model.Schedulable;

import java.util.List;

public class ResultPrinter {

    // Print system header
    public static void printHeader() {
        System.out.println("========================================");
        System.out.println("   FOOD DELIVERY SCHEDULING SYSTEM");
        System.out.println("========================================");
    }

    // Display all loaded orders
    public static void printOrderTable(List<Order> orders) {
        System.out.println("\nLoaded Orders:");
        System.out.printf("%-6s %-15s %-10s %-8s %-10s%n",
                "ID", "Customer", "Deadline", "Profit", "Time");
        System.out.println("--------------------------------------------------");

        for (Order o : orders) {
            System.out.printf("%-6s %-15s %-10d %-8.2f %-10d%n",
                    o.getId(),
                    o.getName(),
                    o.getDeadline(),
                    o.getProfit(),
                    o.getProcessingTime());
        }
    }

    // Print result of a single algorithm
    public static void printResult(SchedulingResult result) {

        System.out.println("\n========================================");
        System.out.println("Algorithm: " + result.algorithmName);
        System.out.println("========================================");

        System.out.println("\nSelected Orders:");
        printTable(result.selectedOrders);

        System.out.println("\nUnselected Orders:");
        printTable(result.unselectedOrders);

        System.out.println("\n----------------------------------------");
        System.out.printf("Total Profit   : %.2f%n", result.totalProfit);
        System.out.printf("Execution Time : %.3f ms%n",
                result.executionTimeNanos / 1_000_000.0);
        System.out.println("========================================\n");
    }

    // Print comparison of all algorithms
    public static void printComparisonTable(List<SchedulingResult> results) {

        System.out.println("\n========================================");
        System.out.println("        ALGORITHM COMPARISON");
        System.out.println("========================================");

        System.out.printf("%-30s %-15s %-15s%n",
                "Algorithm", "Profit", "Time (ms)");
        System.out.println("--------------------------------------------------------");

        for (SchedulingResult r : results) {
            System.out.printf("%-30s %-15.2f %-15.3f%n",
                    r.algorithmName,
                    r.totalProfit,
                    r.executionTimeNanos / 1_000_000.0);
        }

        System.out.println("========================================\n");
    }

    // Helper method for printing orders
    private static void printTable(List<Schedulable> orders) {

        if (orders == null || orders.isEmpty()) {
            System.out.println("  (None)");
            return;
        }

        System.out.printf("%-6s %-15s %-10s %-8s %-10s%n",
                "ID", "Name", "Deadline", "Profit", "Time");
        System.out.println("--------------------------------------------------");

        for (Schedulable o : orders) {
            System.out.printf("%-6s %-15s %-10d %-8.2f %-10d%n",
                    o.getId(),
                    o.getName(),
                    o.getDeadline(),
                    o.getProfit(),
                    o.getProcessingTime());
        }
    }
}
