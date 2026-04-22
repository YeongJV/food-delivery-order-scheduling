package fooddelivery.model;

public class Order implements Schedulable {
    private final String orderId;
    private final String customerName;
    private final String foodItem;
    private final int deadline;
    private final double profit;
    private final int processingTime;
    private final String priority;

    public Order(String orderId,
                 String customerName,
                 String foodItem,
                 int    deadline,
                 double profit,
                 int    processingTime,
                 String priority) {

        if (orderId == null || orderId.isBlank())
            throw new IllegalArgumentException("Order ID cannot be empty.");
        if (deadline <= 0)
            throw new IllegalArgumentException("Deadline must be > 0. Got: " + deadline);
        if (profit < 0)
            throw new IllegalArgumentException("Profit cannot be negative. Got: " + profit);
        if (processingTime <= 0)
            throw new IllegalArgumentException("Processing time must be > 0.");

        this.orderId        = orderId.trim();
        this.customerName   = (customerName != null) ? customerName.trim() : "Unknown";
        this.foodItem       = (foodItem     != null) ? foodItem.trim()     : "Unknown Item";
        this.deadline       = deadline;
        this.profit         = profit;
        this.processingTime = processingTime;
        this.priority       = (priority != null) ? priority.toUpperCase().trim() : "MEDIUM";
    }

    public Order(String orderId, String customerName,
                 String foodItem, int deadline, double profit) {
        this(orderId, customerName, foodItem, deadline, profit, 1, "MEDIUM");
    }


    @Override
    public String getId() {
        return orderId;
    }

    public String getName() {
        return customerName;
    }

    public String getFood() {
        return foodItem;
    }

    @Override
    public int getDeadline() {
        return deadline;
    }

    @Override
    public double getProfit() {
        return profit;
    }

    @Override
    public int getProcessingTime() {
        return processingTime;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String getSummary() {
        return orderId + " | " + customerName + " | " + foodItem +
               " | Deadline: " + deadline +
               " | Profit: " + profit +
               " | Time: " + processingTime +
               " | Priority: " + priority;
    }
}
