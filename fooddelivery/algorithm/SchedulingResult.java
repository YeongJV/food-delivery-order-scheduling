package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.List;

public class SchedulingResult {

    private final String algorithmName;
    private final List<Schedulable> selectedOrders;
    private final List<Schedulable> unselectedOrders;
    private final double totalProfit;
    private final long executionTimeNs;

    public SchedulingResult(String algorithmName,
                            List<Schedulable> selectedOrders,
                            List<Schedulable> unselectedOrders,
                            double totalProfit,
                            long executionTimeNs) {
        this.algorithmName = algorithmName;
        this.selectedOrders = selectedOrders;
        this.unselectedOrders = unselectedOrders;
        this.totalProfit = totalProfit;
        this.executionTimeNs = executionTimeNs;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public List<Schedulable> getSelectedOrders() {
        return selectedOrders;
    }

    public List<Schedulable> getUnselectedOrders() {
        return unselectedOrders;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public long getExecutionTimeNs() {
        return executionTimeNs;
    }
}