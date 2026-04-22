public class ResultPrinter {

    public static void print(SchedulingResult result, String algorithmName) {

        System.out.println("=================================");
        System.out.println("Algorithm: " + algorithmName);
        System.out.println("=================================\n");

        // 1. Job Details
        System.out.println("All Jobs:");
        for (Job job : result.allJobs) {
            System.out.println(job);
        }

        // 2. Selected Jobs
        System.out.println("\nSelected Jobs:");
        for (int i = 0; i < result.scheduledJobs.length; i++) {
            if (result.scheduledJobs[i] != null) {
                System.out.println("Slot " + (i + 1) + ": " + result.scheduledJobs[i]);
            }
        }

        // 3. Total Profit
        System.out.println("\nTotal Profit: " + result.totalProfit);

        // 4. Unselected Jobs
        System.out.println("\nUnselected Jobs:");
        for (Job job : result.allJobs) {
            boolean selected = false;

            for (Job s : result.scheduledJobs) {
                if (s == job) {
                    selected = true;
                    break;
                }
            }

            if (!selected) {
                System.out.println(job);
            }
        }

        System.out.println("\n=================================\n");
    }
}