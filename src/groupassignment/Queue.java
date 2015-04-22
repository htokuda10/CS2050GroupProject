package groupassignment;

/**
 * Created by Jedediah on 4/22/15.
 */

public class Queue  {

    private Node head;   // front of linked list
    private Node tail;  // end of linked list
    private double N  = 0;

    private class Node {
        Object data;    // data object within node
        Node next;      // pointer to next node
    }

    // adding phone calls to the front of the queue/linked list
    public void addToQueue(Customer customer, int priority) {
        if(priority == 1) {                 // assuming priority  1 is phone call add to front of linked list
            Node oldTop = head;
            head = new Node();
            head.data = customer;
            head.next = oldTop;
            N++;

            //update times for customers in queue
            for (Node x = head; x != null; x = x.next) {
            int time = customer.getQuestionTime(); 

            }

        } else {                           // else priority is 0 then add the end of linked list
            Node oldLast = tail;
            tail = new Node();
            tail.data = customer;
            oldLast.next = tail;
            N++;

        } // end if else statement

    } // end addToQueue method


    // removing from front of linked list
    public void removeFromQueue() {
        if (head != null)
            head = head.next;
        N--;
    } // end removeFromQueue


    // getHead method to get the first item in linked list
    public Object getHead() {
        return head;
    } // end getHead method


    // getTail method to get the last item in linked list
    public Object getTail() {
        if(tail != null)
            return tail;
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


