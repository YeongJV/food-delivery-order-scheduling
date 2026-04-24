package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.*;

public class DynamicProgrammingScheduler extends AbstractJobScheduler {

    //Returns algorithm name for display
    @Override 
    public String getAlgorithmName()  { 
        return "Dynamic Programming"; 
    }

    //Returns theoretical time complexity
    @Override 
    public String getTimeComplexity() { 
        return "O(n x D), D = max deadline"; 
    }

    //Returns short explanation of how DP works
    @Override 
    public String getDescription()    {
        return "Builds a 2D table dp[i][t] bottom-up. " +
               "Each cell stores the best profit using the first i orders in t slots. " +
               "Backtracks through the table to find the selected orders.";
    }

    //Executes DP scheduling
    @Override
    protected AlgorithmOutput runAlgorithm(List<? extends Schedulable> orders) {
        List<Schedulable> sorted   = sortByDeadlineAscending(orders); //Sort orders by earliest deadline first
        int n        = sorted.size(); //Number of orders and total available slots
        int maxSlots = getMaxDeadline(sorted);

        // Build DP table
        double[][] dp = new double[n + 1][maxSlots + 1];

        for (int i = 1; i <= n; i++) {
            Schedulable o = sorted.get(i - 1);
            for (int t = 0; t <= maxSlots; t++) {
                dp[i][t] = dp[i - 1][t]; // exclude current order
                if (t >= 1 && t <= o.getDeadline()) {
                    double withOrder = dp[i - 1][t - 1] + o.getProfit();
                    if (withOrder > dp[i][t]) dp[i][t] = withOrder; // keep higher profit
                }
            }
        }

        // Backtrack to find selected orders
        List<Schedulable> selected = new ArrayList<>();
        int t = maxSlots;
        for (int i = n; i >= 1 && t > 0; i--) {
            if (dp[i][t] != dp[i - 1][t]) {
                selected.add(0, sorted.get(i - 1)); // prepend to maintain order
                t--;
            }
        }

        return new AlgorithmOutput(selected);
    }
}
