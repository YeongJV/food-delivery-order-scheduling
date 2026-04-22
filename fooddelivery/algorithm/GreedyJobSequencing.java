package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.ArrayList;
import java.util.List;

public class GreedyJobSequencing extends AbstractJobScheduler {

    @Override
    protected AlgorithmOutput runAlgorithm(List<? extends Schedulable> orders) {
        List<Schedulable> jobs = sortByProfitDescending(orders);
        int maxSlots = getMaxDeadline(jobs);

        Schedulable[] slots = new Schedulable[maxSlots + 1];

        for (Schedulable job : jobs) {
            for (int i = Math.min(job.getDeadline(), maxSlots); i >= 1; i--) {
                if (slots[i] == null) {
                    slots[i] = job;
                    break;
                }
            }
        }

        List<Schedulable> selected = new ArrayList<>();
        for (Schedulable s : slots) {
            if (s != null) selected.add(s);
        }
        return new AlgorithmOutput(selected);
    }

    @Override
    public String getAlgorithmName() { return "Greedy Job Sequencing"; }

    @Override
    public String getTimeComplexity() { return "O(n^2)"; }

    @Override
    public String getDescription() {
        return "Greedy algorithm that sequences jobs by highest profit, filling the latest available slot before each deadline.";
    }
}
