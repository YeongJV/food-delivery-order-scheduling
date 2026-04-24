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
            System.out.println("\n========================================");
            System.out.println("           SELECT ALGORITHM");
            System.out.println("========================================");
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

            while (true) {
                System.out.print("\nRun another test? [Y/N]: ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("y")) { 
                    running = true;  
                    break; 
                }
                if (answer.equals("n")) { 
                    running = false; 
                    break; 
                }
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }

        System.out.println("\n========================================");
        System.out.println("        PROGRAM TERMINATED");
        System.out.println("========================================");
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
            SchedulingResult result = algo.schedule(orders);
            results.add(result);
        }

        // Find fastest algorithm
        SchedulingResult best = results.get(0);

        for (SchedulingResult r : results) {
            if (r.getExecutionTimeNs() < best.getExecutionTimeNs()) {
                best = r;
            }
        }

        // Print comparison table
        ResultPrinter.printComparisonTable(results);

        // Print fastest algorithm
        System.out.println("Fastest Algorithm: " + best.getAlgorithmName());
    }

    // Load data from file or generate random data
    private static List<Order> loadData(Scanner scanner) {
        System.out.println("\n========================================");
        System.out.println("           SELECT DATA INPUT");
        System.out.println("========================================");
        System.out.println("  1. Load from CSV");
        System.out.println("  2. Generate random data");
        System.out.print("Your choice: ");

        int choice = readInt(scanner, 1, 2);

        if (choice == 1) {
            System.out.printf("File path [Enter for default '%s']: ", DEFAULT_FILE);
            String path = scanner.nextLine().trim();
            if (path.isEmpty()) path = DEFAULT_FILE;

            try {
                return new FileHandler().loadFromFile(path);
            } catch (IOException e) {
                System.out.println("Using default data...");
                return generateDefault();
            }
        } else {
            return generateInteractive(scanner);
        }
    }

    // Generate random data with user input
    private static List<Order> generateInteractive(Scanner scanner) {
        int count;

        while (true) {
            try {
                System.out.print("Number of orders [default=10]: ");
                String s = scanner.nextLine().trim();

                count = s.isEmpty() ? 10 : Integer.parseInt(s);

                if (count <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        int minD;

        while (true) {
            try {
                System.out.print("Min deadline [default=1]: ");
                String s = scanner.nextLine().trim();

                minD = s.isEmpty() ? 1 : Integer.parseInt(s);

                if (minD < 1) {
                    System.out.println("Deadline must be at least 1.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        int maxD;

        while (true) {
            try {
                System.out.print("Max deadline [default=6]: ");
                String s = scanner.nextLine().trim();

                maxD = s.isEmpty() ? 6 : Integer.parseInt(s);

                if (maxD < minD) {
                    System.out.println("Max deadline must be >= Min deadline.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        double minP;

        while (true) {
            try {
                System.out.print("Min profit [default=10]: ");
                String s = scanner.nextLine().trim();

                minP = s.isEmpty() ? 10.0 : Double.parseDouble(s);

                if (minP < 0) {
                    System.out.println("Profit cannot be negative.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

        double maxP;

        while (true) {
            try {
                System.out.print("Max profit [default=100]: ");
                String s = scanner.nextLine().trim();

                maxP = s.isEmpty() ? 100.0 : Double.parseDouble(s);

                if (maxP < minP) {
                    System.out.println("Max profit must be >= Min profit.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }

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
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input. Enter %d-%d: ", min, max);
            }
        }
    }
}