package fooddelivery.algorithm;

import fooddelivery.model.Schedulable;
import java.util.List;

public interface JobSchedulingAlgorithm {

    String getAlgorithmName();

    SchedulingResult schedule(List<? extends Schedulable> orders);

    String getTimeComplexity();

    String getDescription();
}
