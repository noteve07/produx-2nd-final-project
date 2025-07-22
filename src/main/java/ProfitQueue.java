
/*
 * ProfitQueue.java
 *
 * This class implements a queue to store profit values for the last 7 days with a fixed capacity. 
 * It uses the enqueue operation to add the most recent profit data and automatically removes 
 * the oldest profit (over 7 days) when the queue reaches its capacity, ensuring that only the 
 * last 7 days of profit data are retained. This structure is ideal for maintaining a rolling window 
 * of the most recent profit values while discarding outdated information.
 */

class ProfitQueue {
    private Double[] elements;
    private int front, rear, size, capacity;

    
    private class ProfitData {
        double value; 
        String day;   
        double net;   
    }
    private ProfitData[] profitData;

    public ProfitQueue(int capacity) {
        this.capacity = capacity;
        this.elements = new Double[capacity];
        this.profitData = new ProfitData[capacity]; // Initialize the ProfitData array
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void enqueue(Double profit) {
        // enqueue most recent profit data
        if (size == capacity) {
            dequeue(); // If the queue is full, dequeue the oldest item
        }
        rear = (rear + 1) % capacity;
        elements[rear] = profit;

        // create new profit data
        ProfitData newData = new ProfitData();
        newData.value = profit;
        newData.day = "Day " + (size + 1); // Example: "Day 1", "Day 2" (adjust for real day names)
        newData.net = size > 0 ? profit - elements[rear - 1 < 0 ? capacity - 1 : rear - 1] : 0;
        profitData[rear] = newData;

        size++;
    }

    public Double dequeue() {
        // dequeue profit data
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        Double profit = elements[front];
        front = (front + 1) % capacity;
        size--;
        return profit;
    }

    public Double peek(int index) {
        // peek through a specific profit data
        if (index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return elements[(front + index) % capacity];
    }

    public int size() {
        return size;
    }

    public double getTotalProfit() {
        // Existing total profit calculation
        double total = 0;
        for (int i = 0; i < size; i++) {
            total += peek(i);
        }
        return total;
    }

    public ProfitData getCurrentDayProfit() {
        // get the profit for the current day
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        return profitData[rear];
    }
    
    public double calculateNetIncome() {
        // calculate the net income from the previous day
        if (size < 2) {
            throw new IllegalStateException("Not enough data for net income");
        }
        int previousIndex = rear - 1; 
        double netIncome = profitData[rear].value - profitData[previousIndex].value;
        return netIncome; 

    }

    public void removeOldProfit() {
        // remove a profit that is over 7 days
        if (size > 7) {
            dequeue(); 
        }
    }
}