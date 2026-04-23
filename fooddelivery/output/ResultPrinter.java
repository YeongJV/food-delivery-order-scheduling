package fooddelivery.output;

import fooddelivery.algorithm.SchedulingResult;
import fooddelivery.model.Order;
import fooddelivery.model.Schedulable;

import java.util.List;

public class ResultPrinter {

    // Print system header
    public static void printHeader() {
        System.out.println("========================================");
        System.out.println("    FOOD DELIVERY SCHEDULING SYSTEM");
        System.out.println("========================================");
    }

    // Display all loaded orders
    public static void printOrderTable(List<Order> orders) {
        System.out.println("\n========================================");
        System.out.println("            LOADED ORDERS");
        System.out.println("========================================");
        System.out.printf("%-8s %-18s %-20s %-10s %-10s%n",
        "ID", "Customer", "Food Item", "Deadline", "Profit");
        System.out.println("---------------------------------------------------------------------");

        for (Order o : orders) {
            System.out.printf("%-8s %-18s %-20s %-10d %-10.2f%n",
                    o.getId(),
                    o.getName(),
                    o.getFood(),
                    o.getDeadline(),
                    o.getProfit());
        }
    }

    // Print result of a single algorithm
    public static void printResult(SchedulingResult result) {

        if (result == null) {
            System.out.println("No result.");
            return;
        }

        System.out.println("\n==============================================");
        System.out.println("Algorithm: " + result.getAlgorithmName());
        System.out.println("==============================================");

        System.out.println("\n--- Selected Orders ---");
        printTable(result.getSelectedOrders());

        System.out.println("\n--- Unselected Orders ---");
        printTable(result.getUnselectedOrders());

        System.out.println("\n----------------------------------------");
        System.out.printf("Total Profit   : %.2f%n", result.getTotalProfit());
        System.out.printf("Execution Time : %.3f ms%n",
                result.getExecutionTimeNs() / 1_000_000.0);
        System.out.println("========================================\n");
    }

    // Print comparison of all algorithms
    public static void printComparisonTable(List<SchedulingResult> results) {

        System.out.println("\n========================================");
        System.out.println("        ALGORITHM COMPARISON");
        System.out.println("========================================");

        System.out.printf("%-35s %-12s %-12s%n",
                "Algorithm", "Profit", "Time (ms)");
        System.out.println("-----------------------------------------------------------");

        for (SchedulingResult r : results) {
            System.out.printf("%-35s %-12.2f %-12.3f%n",
                    r.getAlgorithmName(),
                    r.getTotalProfit(),
                    r.getExecutionTimeNs() / 1_000_000.0);
        }

        System.out.println("========================================\n");
    }

    // Helper method for printing orders
    private static void printTable(List<Schedulable> orders) {

        if (orders == null || orders.isEmpty()) {
            System.out.println("  (No orders selected)");
            return;
        }

        System.out.printf("%-8s %-18s %-20s %-10s %-12s %-8s%n",
        "ID", "Customer", "Food Item", "Deadline", "Profit (RM)", "Time");
        System.out.println("-------------------------------------------------------------------------------");

        for (Schedulable o : orders) {
            System.out.printf("%-8s %-18s %-20s %-10d %-12.2f %-8d%n",
                    o.getId(),
                    o.getName(),
                    o.getFood(),
                    o.getDeadline(),
                    o.getProfit(),
                    o.getProcessingTime());
        }
    }
}
