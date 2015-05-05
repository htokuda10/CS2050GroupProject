package groupassignment;
/**
 * The Queue class is a queue that places created nodes and the head or at the
 * tail of the queue dependent on the customer priority.  If the customer has a
 * priority of 1 they will be pushed to the head, if the priority is a 0 then 
 * they will be enqueued at the tail.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public class Queue  {
    // Front of linked list.
    private Node head;
    // End of linked list.
    private Node tail;
    // Size of queue.
    private static int size  = 0;
    
    // Node object.
    private class Node {
        // Data object within node.
        Customer data = null;   
        // Pointer to next node.
        Node next = null;      
    }
   /**
    * addToQueue creates a new node that contains the customer data and adds it
    * to the queue according to priority.
    * @param customer new customer.
    * @param priority priority level of the customer, 1 = call-in, 0 = walk-in.
    */
    public void addToQueue(Customer customer, int priority) {
        try{
            // If queue is empty, set added node to both head and tail.
            if(size == 0){
                head = new Node();
                head.data = customer;
                tail = head;
            }
            else {
                // If customer priority is 1 add to the front of linked list.
                if(priority == 1) {                 
                    Node oldHead = head;
                    head = new Node();
                    head.data = customer;
                    head.next = oldHead;
                    // Update times for customers in queue behind the head node.
                    int time = Integer.parseInt(customer.getQuestionTime());
                    for (Node x = head.next; x != null; x = x.next) {
                        x.data.updateTime(time);
                    }
                } 
                // Else customer priority is 0 add to the end of linked list.
                else {                           
                    Node oldLast = tail;
                    tail = new Node();
                    tail.data = customer;
                    oldLast.next = tail;
                }
            } // end if else statement
        }
        catch(NullPointerException ex0){
            // Do nothing.
        }
        size++;
    } // end addToQueue method
    /**
     * removeFromQueue removes the head of the linked list and returns the
     * customer contained in the node.
     * @return Customer.
     */
    public Customer removeFromQueue() {
        Node returnNode = null;
        if (head != null){
            returnNode = head;
            head = head.next;
            --size;
        }
        return returnNode.data;
    } // end removeFromQueue
    /**
     * getHead gets the customer at the head of the linked list.
     * @return Customer.
     */
    public Customer getHead() {
        return head.data;
    }
    /**
     * getTail gets the customer at the tail of the linked list.
     * @return Customer.
     */
    public Customer getTail() {
        if(tail != null)
            return tail.data;
        return null;
    }
    /**
     * isEmpty returns a boolean if the linked list contains no nodes.
     * @return boolean.
     */
    public boolean isEmpty() {
        return head == null;
    }
    /**
     * size returns the size of the linked list.
     * @return int.
     */
    public int size() {
        return size;
    }
} // end Queue class