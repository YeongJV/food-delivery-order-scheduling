package fooddelivery.util;

import fooddelivery.model.Order;
import java.util.*;

public class RandomDataGenerator {

    private static final String[] FOOD_ITEMS = {
        "Nasi Lemak", "Char Kuey Teow", "Roti Canai", "Mee Goreng",
        "Laksa", "Burger Ramly", "Ayam Goreng", "Tom Yam Seafood",
        "Fried Rice", "Beef Rendang", "Satay Set", "Pizza Margherita",
        "Chicken Chop", "Spaghetti Bolognese", "Dim Sum Basket"
    };

    private static final String[] CUSTOMERS = {
        "Ali Ahmad", "Siti Nurhaliza", "Raj Kumar", "Mei Ling",
        "Hafiz Zain", "Priya Nair", "David Tan", "Nurul Ain",
        "Kevin Lim", "Fatimah Ismail", "Jason Wong", "Aisha Mohd"
    };

    private static final String[] PRIORITIES = { "HIGH", "MEDIUM", "LOW" };

    public List<Order> generate(int count, int minDeadline, int maxDeadline, double minProfit, double maxProfit) {
        if (count       <= 0)           
          throw new IllegalArgumentException("Count must be >= 1.");

        if (minDeadline <  1)         
          throw new IllegalArgumentException("Min deadline must be >= 1.");
      
        if (minDeadline >  maxDeadline) 
          throw new IllegalArgumentException("Min deadline > max deadline.");
      
        if (minProfit   <  0)          
          throw new IllegalArgumentException("Min profit cannot be negative.");
      
        if (minProfit   >  maxProfit)   
          throw new IllegalArgumentException("Min profit > max profit.");

        List<Order> orders = new ArrayList<>();
        Random      rng    = new Random();

        for (int i = 1; i <= count; i++) {
            String orderId  = String.format("ORD-%03d", i);
            String customer = CUSTOMERS [rng.nextInt(CUSTOMERS.length)];
            String food     = FOOD_ITEMS[rng.nextInt(FOOD_ITEMS.length)];
            int    deadline = minDeadline + rng.nextInt(maxDeadline - minDeadline + 1);
            double profit   = minProfit+ Math.round((maxProfit - minProfit) * rng.nextDouble() * 100.0) / 100.0;
            String priority = PRIORITIES[rng.nextInt(PRIORITIES.length)];

            orders.add(new Order(orderId, customer, food, deadline, profit, 1, priority));
        }

        System.out.printf("Generated %d random orders.%n", count);
        System.out.printf("  Deadline range : %d to %d%n", minDeadline, maxDeadline);
        System.out.printf("  Profit range   : RM %.2f to RM %.2f%n%n", minProfit, maxProfit);
        return orders;
    }
}
