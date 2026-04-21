import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreedyJobSequencing extends AbstractJobSequencing {

    @Override
    public String getStrategyName() {
        return "Greedy Job Sequencing";
    }

    @Override
    public JobResult scheduleJobs(List<Job> jobs) {
        JobResult result = new JobResult();

        if (jobs == null || jobs.isEmpty()) {
            return result;
        }

        List<Job> sortedJobs = new ArrayList<>(jobs);
        sortedJobs.sort(Comparator.comparingInt(Job::getProfit).reversed());

        int maxDeadline = getMaxDeadline(sortedJobs);

        Job[] slots = new Job[maxDeadline];

        for (Job job : sortedJobs) {
            boolean assigned = false;

            // Try to place the job in the latest available slot
            for (int i = Math.min(job.getDeadline(), maxDeadline) - 1; i >= 0; i--) {
                if (slots[i] == null) {
                    slots[i] = job;
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                result.addUnselectedJob(job);
            }
        }

        // Collect selected jobs in slot order
        for (Job slotJob : slots) {
            if (slotJob != null) {
                result.addSelectedJob(slotJob);
            }
        }

        return result;
    }
}
