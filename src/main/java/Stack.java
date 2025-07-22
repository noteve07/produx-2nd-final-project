/*  
 * Stack.java  
 *  
 * A custom stack implementation designed to manage the undo functionality in the application.  
 * It uses a linked list to efficiently track and retrieve user actions (Action objects)  
 * for undo operations. This stack also updates the history table, allowing users to  
 * seamlessly view and revert changes.  
 */  

class Stack {
    
    private static class Node {
        // node class for the linked list
        Action action;
        Node next;

        public Node(Action action) {
            this.action = action;
            this.next = null;
        }
    }

    // initialize top of stack and capacity
    private Node top; 
    private int size;

    public Stack() {
        // constructor
        this.top = null;
        this.size = 0;
    }

    
    public void push(Action action) {
        // push a new action onto the stack
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    
    public Action pop() {
        // pop the most recent action from the stack
        if (isEmpty()) {
            return null;
        }
        Action action = top.action;
        top = top.next;
        size--;
        return action;
    }

    public Action peek() {
        // peek at the most recent action without removing it
        return isEmpty() ? null : top.action;
    }

    public boolean isEmpty() {
        // check if the stack is empty
        return top == null;
    }

    public int getSize() {
        // get the size of the stack
        return size;
    }

    public void clear() {
        // clear the stack
        top = null;
        size = 0;
    }
}
