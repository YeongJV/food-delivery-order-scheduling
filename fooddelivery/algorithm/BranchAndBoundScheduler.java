package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;

import java.util.*;

public class BranchAndBoundScheduler extends AbstractJobScheduler {

    @Override
    public String getAlgorithmName() {
        return "Branch and Bound";
    }

    @Override
    public String getTimeComplexity() {
        return "O(2^n) worst case, but faster in practice due to pruning";
    }

    @Override
    public String getDescription() {
        return "Uses branching and pruning to find maximum profit schedule.";
    }

    @Override
    protected AlgorithmOutput runAlgorithm(List<? extends Schedulable> orders) {

        List<Schedulable> jobs = sortByProfitDescending(orders);
        int n = jobs.size();
        int maxSlots = getMaxDeadline(jobs);

        PriorityQueue<Node> pq = new PriorityQueue<>(
                (a, b) -> Double.compare(b.bound, a.bound)
        );

        Node root = new Node(-1, 0, new boolean[maxSlots + 1], new ArrayList<>());
        root.bound = calculateBound(root, jobs, n, maxSlots);

        pq.add(root);

        double bestProfit = 0;
        List<Schedulable> bestSet = new ArrayList<>();

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // Skip node if it cannot improve current best
            if (current.bound <= bestProfit) continue;

            int next = current.level + 1;
            if (next >= n) continue;

            Schedulable job = jobs.get(next);

            // Include
            int slot = findSlot(current.slots, job.getDeadline(), maxSlots);

            if (slot != -1) {
                boolean[] newSlots = Arrays.copyOf(current.slots, current.slots.length);
                newSlots[slot] = true;

                List<Schedulable> newList = new ArrayList<>(current.selected);
                newList.add(job);

                Node include = new Node(
                        next,
                        current.profit + job.getProfit(),
                        newSlots,
                        newList
                );

                include.bound = calculateBound(include, jobs, n, maxSlots);

                if (include.profit > bestProfit) {
                    bestProfit = include.profit;
                    bestSet = new ArrayList<>(include.selected);
                }

                if (include.bound > bestProfit) {
                    pq.add(include);
                }
            }

            // Exclude
            Node exclude = new Node(
                    next,
                    current.profit,
                    Arrays.copyOf(current.slots, current.slots.length),
                    new ArrayList<>(current.selected)
            );

            exclude.bound = calculateBound(exclude, jobs, n, maxSlots);

            if (exclude.bound > bestProfit) {
                pq.add(exclude);
            }
        }

        return new AlgorithmOutput(bestSet);
    }

    // Bound Calculation
    private double calculateBound(Node node,
                                  List<Schedulable> jobs,
                                  int n,
                                  int maxSlots) {

        double bound = node.profit;
        boolean[] temp = Arrays.copyOf(node.slots, node.slots.length);

        for (int i = node.level + 1; i < n; i++) {
            Schedulable job = jobs.get(i);
            int slot = findSlot(temp, job.getDeadline(), maxSlots);

            if (slot != -1) {
                bound += job.getProfit();
                temp[slot] = true;
            }
        }
        return bound;
    }

    // Find Slot
    private int findSlot(boolean[] slots, int deadline, int maxSlots) {
        for (int i = Math.min(deadline, maxSlots); i >= 1; i--) {
            if (!slots[i]) return i;
        }
        return -1;
    }

    // Node
    private static class Node {
        private int level;
        private double profit;
        private boolean[] slots;
        private List<Schedulable> selected;
        private double bound;

        Node(int level, double profit, boolean[] slots, List<Schedulable> selected) {
            this.level = level;
            this.profit = profit;
            this.slots = slots;
            this.selected = selected;
        }
    }
}
