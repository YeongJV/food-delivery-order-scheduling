package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractJobScheduler implements JobSchedulingAlgorithm {

    @Override
    public final SchedulingResult schedule(List<? extends Schedulable> orders) {
        if (orders == null || orders.isEmpty()) {
            return new SchedulingResult(getAlgorithmName(),
                    new ArrayList<>(), new ArrayList<>(), 0.0, 0L);
        }

        long start = System.nanoTime();
        AlgorithmOutput output = runAlgorithm(orders);
        long elapsed = System.nanoTime() - start;

        // Determine unselected orders
        List<Schedulable> unselected = new ArrayList<>();
        for (Schedulable order : orders) {
            if (!output.selected.contains(order)) {
                unselected.add(order);
            }
        }

        double totalProfit = output.selected.stream()
                .mapToDouble(Schedulable::getProfit)
                .sum();

        return new SchedulingResult(
                getAlgorithmName(),
                output.selected,
                unselected,
                totalProfit,
                elapsed
        );
    }

    protected abstract AlgorithmOutput runAlgorithm(List<? extends Schedulable> orders);

    protected List<Schedulable> sortByProfitDescending(List<? extends Schedulable> orders) {
        List<Schedulable> sorted = new ArrayList<>(orders);
        sorted.sort(Comparator.comparingDouble(Schedulable::getProfit).reversed());
        return sorted;
    }

    protected List<Schedulable> sortByDeadlineAscending(List<? extends Schedulable> orders) {
        List<Schedulable> sorted = new ArrayList<>(orders);
        sorted.sort(Comparator.comparingInt(Schedulable::getDeadline));
        return sorted;
    }

    protected int getMaxDeadline(List<? extends Schedulable> orders) {
        return orders.stream()
                .mapToInt(Schedulable::getDeadline)
                .max()
                .orElse(0);
    }

    public void printAlgorithmHeader() {
        System.out.println("Running: " + getAlgorithmName());
        System.out.println("Description: " + getDescription());
        System.out.println("Time Complexity: " + getTimeComplexity());
        System.out.println();
    }

    protected static class AlgorithmOutput {
        public final List<Schedulable> selected;

        public AlgorithmOutput(List<Schedulable> selected) {
            this.selected = selected;
        }
    }
}
