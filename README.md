# food-delivery-order-scheduling
Project Overview
Solves the Job Sequencing Problem in a food delivery scenario
Objective: maximize total profit while meeting all deadlines
Single delivery driver → one order per time slot
Problem Definition
Each order has:
Deadline (time limit)
Profit (value of completing order)
Not all orders can be selected due to limited slots
System must:
Select optimal subset of orders
Arrange valid scheduling sequence
Algorithms Implemented
Greedy Job Sequencing
Selects highest profit first
Fast but not always optimal
Dynamic Programming
Uses table-based optimization
Guarantees optimal solution
Branch and Bound
Explores solution space with pruning
Optimal but computationally expensive
Task Priority Scheduling (Weighted)
Greedy-based with structured prioritization
Efficient and scalable
System Design
Built using Object-Oriented Programming (OOP)
Key concepts:
Encapsulation
Abstraction (Schedulable interface)
Polymorphism (multiple algorithms)
Modular structure:
Input (FileHandler, RandomDataGenerator)
Processing (Algorithms)
Output (ResultPrinter)
Input Methods
Load from CSV file (default file supported)
Generate random orders with user-defined parameters
Output Features
Displays:
All orders (table format)
Selected and unselected orders
Total profit
Execution time
Comparison table for all algorithms
Key Features
Multiple algorithm comparison
User-friendly console interface
Strong validation and error handling
Extensible design for adding new algorithms
Conclusion
Different algorithms can solve the same problem effectively
Trade-off between execution time and optimality
Suitable for real-world scheduling applications
