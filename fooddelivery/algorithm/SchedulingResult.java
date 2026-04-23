package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.Collections;
import java.util.List;

public class SchedulingResult {

    private final String algorithmName;
    private final List<Schedulable> selectedOrders;
    private final List<Schedulable> unselectedOrders;
    private final double totalProfit;
    private final long executionTimeNs;

    public SchedulingResult(String name,
                            List<Schedulable> selected,
                            List<Schedulable> unselected,
                            double profit,
                            long time) {
        this.algorithmName = name;
        this.selectedOrders = Collections.unmodifiableList(selected);
        this.unselectedOrders = Collections.unmodifiableList(unselected);
        this.totalProfit = profit;
        this.executionTimeNs = time;
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
