package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.ArrayList;
import java.util.List;

public class WeightedTaskScheduling extends AbstractJobScheduler {

    @Override
    protected AlgorithmOutput runAlgorithm(List<? extends Schedulable> orders) {
        List<Schedulable> jobs = sortByProfitDescending(orders);
        int maxSlots = getMaxDeadline(jobs);

        boolean[] filled = new boolean[maxSlots + 1];
        List<Schedulable> selected = new ArrayList<>();

        for (Schedulable job : jobs) {
            for (int slot = Math.min(job.getDeadline(), maxSlots); slot >= 1; slot--) {
                if (!filled[slot]) {
                    filled[slot] = true;
                    selected.add(job);
                    break;
                }
            }
        }

        return new AlgorithmOutput(selected);
    }

    @Override
    public String getAlgorithmName() {
        return "Task Priority Scheduling (Weighted)";
    }

    @Override
    public String getTimeComplexity() {
        return "O(n^2)";
    }

    @Override
    public String getDescription() {
        return "Greedy algorithm that schedules jobs by highest profit first, assigning each to the latest available slot before its deadline.";
    }
}
