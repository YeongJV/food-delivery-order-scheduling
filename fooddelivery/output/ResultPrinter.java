package fooddelivery.output;

import fooddelivery.algorithm.SchedulingResult;
import fooddelivery.model.Schedulable;

public class ResultPrinter {

    public static void print(SchedulingResult result) {

        System.out.println("=================================");
        System.out.println("Algorithm: " + result.algorithmName);
        System.out.println("=================================\n");

        System.out.println("Selected Jobs:");
        for (Schedulable job : result.selectedOrders) {
            System.out.println("  " + job.getSummary());
        }

        System.out.println("\nUnselected Jobs:");
        for (Schedulable job : result.unselectedOrders) {
            System.out.println("  " + job.getSummary());
        }

        System.out.printf("%nTotal Profit: %.2f%n", result.totalProfit);
        System.out.printf("Execution Time: %.3f ms%n", result.executionTimeNanos / 1_000_000.0);
        System.out.println("\n=================================\n");
    }
}
