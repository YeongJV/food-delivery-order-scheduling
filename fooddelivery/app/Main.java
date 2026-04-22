package fooddelivery.app;

import fooddelivery.algorithm.*;
import fooddelivery.model.Order;
import fooddelivery.output.ResultPrinter;
import fooddelivery.util.FileHandler;
import fooddelivery.util.RandomDataGenerator;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final String DEFAULT_FILE = "data/orders.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ResultPrinter.printHeader();

        // Load data (file or random)
        List<Order> orders = loadData(scanner);
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders loaded. Exiting.");
            return;
        }

        ResultPrinter.printOrderTable(orders);

        boolean running = true;
        while (running) {
            System.out.println("\nSELECT AN ALGORITHM:");
            System.out.println("  1. Branch and Bound");
            System.out.println("  2. Greedy");
            System.out.println("  3. Dynamic Programming");
            System.out.println("  4. Weighted Task Scheduling");
            System.out.println("  5. Run ALL algorithms");
            System.out.println("  0. Exit");
            System.out.print("Your choice: ");

            int choice = readInt(scanner, 0, 5);

            if (choice == 0) break;

            if (choice == 5) {
                runAllAndCompare(orders);
            } else {
                JobSchedulingAlgorithm algo = getAlgorithm(choice);
                SchedulingResult result = algo.schedule(orders);
                ResultPrinter.printResult(result);
            }

            System.out.print("\nRun again? (y/n): ");
            running = scanner.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.println("Program terminated.");
        scanner.close();
    }

    // Select algorithm based on user choice
    private static JobSchedulingAlgorithm getAlgorithm(int choice) {
        switch (choice) {
            case 1: return new BranchAndBoundScheduler();
            case 2: return new GreedyJobSequencing();
            case 3: return new DynamicProgrammingScheduler();
            case 4: return new WeightedTaskScheduling();
            default: throw new IllegalArgumentException("Invalid choice.");
        }
    }

    // Run all algorithms and compare results
    private static void runAllAndCompare(List<Order> orders) {
        List<JobSchedulingAlgorithm> algorithms = Arrays.asList(
                new BranchAndBoundScheduler(),
                new GreedyJobSequencing(),
                new DynamicProgrammingScheduler(),
                new WeightedTaskScheduling()
        );

        List<SchedulingResult> results = new ArrayList<>();
        for (JobSchedulingAlgorithm algo : algorithms) {
            results.add(algo.schedule(orders));
        }

        ResultPrinter.printComparisonTable(results);
    }

    // Load data from file or generate random data
    private static List<Order> loadData(Scanner scanner) {
        System.out.println("SELECT DATA INPUT:");
        System.out.println("  1. Load from CSV");
        System.out.println("  2. Generate random data");
        System.out.print("Choice: ");

        int choice = readInt(scanner, 1, 2);

        if (choice == 1) {
            System.out.printf("File path [Enter for default '%s']: ", DEFAULT_FILE);
            String path = scanner.nextLine().trim();
            if (path.isEmpty()) path = DEFAULT_FILE;

            try {
                return new FileHandler().loadFromFile(path);
            } catch (IOException e) {
                System.out.println("Error loading file. Using default data.");
                return generateDefault();
            }
        } else {
            return generateInteractive(scanner);
        }
    }

    // Generate random data with user input
    private static List<Order> generateInteractive(Scanner scanner) {
        System.out.print("Number of orders [default=10]: ");
        String s = scanner.nextLine().trim();
        int count = s.isEmpty() ? 10 : Integer.parseInt(s);

        System.out.print("Min deadline [default=1]: ");
        s = scanner.nextLine().trim();
        int minD = s.isEmpty() ? 1 : Integer.parseInt(s);

        System.out.print("Max deadline [default=6]: ");
        s = scanner.nextLine().trim();
        int maxD = s.isEmpty() ? 6 : Integer.parseInt(s);

        System.out.print("Min profit [default=10]: ");
        s = scanner.nextLine().trim();
        double minP = s.isEmpty() ? 10.0 : Double.parseDouble(s);

        System.out.print("Max profit [default=100]: ");
        s = scanner.nextLine().trim();
        double maxP = s.isEmpty() ? 100.0 : Double.parseDouble(s);

        return new RandomDataGenerator().generate(count, minD, maxD, minP, maxP);
    }

    // Default dataset
    private static List<Order> generateDefault() {
        return new RandomDataGenerator().generate(10, 1, 6, 10.0, 100.0);
    }

    // Safe integer input
    private static int readInt(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("Enter %d-%d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input. Enter %d-%d: ", min, max);
            }
        }
    }
}