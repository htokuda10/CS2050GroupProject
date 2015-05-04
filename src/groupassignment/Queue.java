package groupassignment;
/**
 * Created by Jedediah on 4/22/15.
 */
public class Queue  {
    
    private Node head;  // front of linked list
    private Node tail;  // end of linked list
    /*
       Changed by Yuki: changed 'double N' to 'int N'.
    */
    private static int N  = 0;

    private class Node {
        /*
           Changed by Yuki:  Changed 'Object data' to 'Customer data'.  This
           stores the information as a Customer object instead of storing it as
           a general object.
        */
        // data object within node
        Customer data = null;   
        // pointer to next node
        Node next = null;      
    }

    // adding phone calls to the front of the queue/linked list
    public void addToQueue(Customer customer, int priority) {
        try{
            /*
               Changed by Yuki:  This creates a head whether priority is to push
               or enqueue, and tail will equal head so that way the first node
               in queue is always pointed to by the head and tail pointers.
            */
            if(N == 0){
                head = new Node();
                head.data = customer;
                tail = head;
            }
            else {
                // assuming priority  1 is phone call add to front of linked list.
                if(priority == 1) {                 
                    Node oldHead = head;
                    head = new Node();
                    head.data = customer;
                    head.next = oldHead;
                    /*
                       Changed by Yuki : parseInt instead of changing to int.
                       Changing customer.getQuestionTime to return an int would
                       require modifying the log input.  This is cleaner and
                       more simple.
                    */
                    // update times for customers in queue.
                    int time = Integer.parseInt(customer.getQuestionTime());
                    for (Node x = head.next; x != null; x = x.next) {
                        x.data.updateTime(time);
                    }
                } 
                // else priority is 0 then add the end of linked list.
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
        N++;
    } // end addToQueue method

    /*
       Changed by Yuki: Changed return variable from void to Customer.  Need the
       data from the customer removed from queue to store into the Log database.
    */
    // removing from front of linked list
    public Customer removeFromQueue() {
        Node returnNode = null;
        if (head != null){
            returnNode = head;
            head = head.next;
            --N;
        }
        return returnNode.data;
    } // end removeFromQueue

    /*
       Changed by Yuki: Changed the return variable from Object to Customer to
       return a Customer object rather than a general object.
    
       Changed by Yuki: Changed the 'return head;' to 'return head.data'.
    */
    // getHead method to get the first item in linked list
    public Customer getHead() {
        return head.data;
    } // end getHead method

    /*
       Changed by Yuki: Changed the return variable from Object to Customer to
       return a Customer object rather than a general object.
    
       Changed by Yuki: Changed the 'return tail;' to 'return tail.data'.
    */
    // getTail method to get the last item in linked list
    public Customer getTail() {
        if(tail != null)
            return tail.data;
        return null;
    } // end getTail method

    // isEmpty method to see if linked list is empty
    public boolean isEmpty() {
        return head == null;
    }
    // Returns the size of the queue.
    public int size() {
        return N;
    }
    
//    public void getSizeNew() {
//        int queueSize = 0;
//        for (Node x = head.getNext(); x != null; x = x.getNext()) {
//            queueSize++;
//            System.out.println("The size of the queue is " + queueSize);
//        }
} // end Queue class