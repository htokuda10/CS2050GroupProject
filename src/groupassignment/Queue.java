package groupassignment;

/**
 * Created by Jedediah on 4/22/15.
 */

public class Queue  {

    private Node head;   // front of linked list
    private Node tail;  // end of linked list
    /*
       Changed by Yuki: changed 'double N' to 'int N'.
    */
    private int N  = 0;

    private class Node {
        /*
           Changed by Yuki:  Changed 'Object data' to 'Customer data'.  This
           stores the information as a Customer object instead of converting it
           to a general object.
        */
        Customer data;    // data object within node
        Node next;      // pointer to next node
    }

    // adding phone calls to the front of the queue/linked list
    public void addToQueue(Customer customer, int priority) {
        if(priority == 1) {                 // assuming priority  1 is phone call add to front of linked list
            Node oldTop = head;
            head = new Node();
            head.data = customer;
            head.next = oldTop;
            /*
               Changed by Yuki: Added 'if(N == 0)' because if the queue is empty
               and a call-in customer happens, this will create a head but no
               tail, which prevents the ability to get the last person in queues
               finish time.
            */
            if(N == 0){
                tail = head;
            }
            N++;

            //update times for customers in queue
            
            int time = Integer.parseInt(customer.getQuestionTime());
            for (Node x = head; x != null; x = x.next) {
                System.out.println(x.data.getFirstName() + " time updated.");
                /*
                   Changed by Yuki : parseInt instead of changing to int.
                   Changing customer.getQuestionTime to return an int would
                   require modifying the log input.  This is cleaner and more
                   simple.
                */
                x.data.updateTime(time);
            }

        } else {                           // else priority is 0 then add the end of linked list
            Node oldLast = tail;
            tail = new Node();
            tail.data = customer;
            oldLast.next = tail;
            N++;

        } // end if else statement

    } // end addToQueue method

    /*
       Changed by Yuki: Changed return variable from void to Customer.  Need the
       data from the customer removed from queue to store into the Log database.
    */
    // removing from front of linked list
    public Customer removeFromQueue() {
        Node returnNode = null;
        if (head != null)
            returnNode = head;
            head = head.next;
        N--;
        return returnNode.data;
    } // end removeFromQueue

    /*
       Changed by Yuki: Changed the return to a Customer object rather than a
       general object.
       Changed by Yuki: Changed the 'return head;' to 'return head.data'.
    */
    // getHead method to get the first item in linked list
    public Customer getHead() {
        return head.data;
    } // end getHead method

    /*
       Changed by Yuki: Changed the return to a Customer object rather than a
       general object.
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

    public double size() {
        return N;
    }

} // end Queue class


