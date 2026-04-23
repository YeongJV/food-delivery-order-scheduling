package fooddelivery.model;

public interface Schedulable {
    String getId();
    String getName();
    String getFood();
    int getDeadline();
    double getProfit();
    int getProcessingTime();
    String getSummary();
}
