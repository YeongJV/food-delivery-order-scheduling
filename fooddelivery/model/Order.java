public class Order {
    private String id;
    private String description;
    private int deadline;
    private int profit;

    public Order(String id, String description, int deadline, int profit) {
        this.id = id;
        this.description = description;
        this.deadline = deadline;
        this.profit = profit;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return id + " | " + description + " | Deadline: " + deadline + " | Profit: " + profit;
    }
}
