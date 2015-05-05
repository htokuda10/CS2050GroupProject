package groupassignment;
/**
 * GroupAssignment is a program that is designed to simulate an office business
 * that has two types of customers, walk-ins and call-ins.  A GUI will appear
 * upon startup which will allow the user to select four time values: simulation
 * time, walk-in mean time, call-in mean time, and time it takes to answer a
 * question or question mean time.  The program will then run the simulation,
 * and using a linked list as the queue, it will place walk-in customers at the
 * tail of the linked list and call-in customers will be placed at the head of
 * the linked list.  Poisson's random formula is used to determine three values
 * using the mean values provided by the user prior to starting the simulation.
 * Once the simulation has started, it uses a loop to simulate the time, each
 * iteration counts as one second.  When the loop reaches the walk-in/call-in
 * value, a customer is created and placed into the queue based on the rules
 * provided above, and new values are created relative to the customer created;
 * a walk-in customer created equals a new Poissons's value assigned to the
 * walk-in variable to determine when the next walk-in customer will be created,
 * and repeat until the user defined simulation time has been reached.  The 
 * program will print out the results after the simulation has completed.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public class GroupAssignment {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}
