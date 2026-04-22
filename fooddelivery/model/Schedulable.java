public interface Schedulable {
    int getDeadline();
    int getProfit();
}
public class Order implements Schedulable {
    

    @Override
    public int getDeadline() {
        return deadline;
    }

    @Override
    public int getProfit() {
        return profit;
    }
}
