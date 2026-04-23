package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.Collections;
import java.util.List;

public class SchedulingResult {

    public final String algorithmName;
    public final List<Schedulable> selectedOrders;
    public final List<Schedulable> unselectedOrders;
    public final double totalProfit;
    public final long executionTimeNanos;

    public SchedulingResult(String algorithmName, List<Schedulable> selectedOrders, List<Schedulable> unselectedOrders, double totalProfit, long executionTimeNanos) {
        this.algorithmName = algorithmName;
        this.selectedOrders = Collections.unmodifiableList(selectedOrders);
        this.unselectedOrders = Collections.unmodifiableList(unselectedOrders);
        this.totalProfit = totalProfit;
        this.executionTimeNanos = executionTimeNanos;
    }
}
