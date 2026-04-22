package fooddelivery.model;

public interface Schedulable {
    String getId();
    String getName();
    int getDeadline();
    double getProfit();
    int getProcessingTime();
    String getSummary();
}
