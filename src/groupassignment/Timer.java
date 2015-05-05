package groupassignment;

import javax.swing.JTextArea;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The Timer class will create customers based on a random time calculated by a 
 * Poisson formula.  After the customer is created, it will then be placed at
 * the head or the tail of a queue dependent on it's priority level.  Once the
 * customer has reached the head of the queue, the timer will dequeue the head
 * after the customer's finish time equals the current timer count.  The
 * customers finish time is calculated by the question time plus the customer
 * directly in fronts finish time.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public final class Timer {
    // Stores value of when the next walk-in customer is created.
    private static int walkInCustomerTime;
    // Stores value of when the next call-in customer is created.
    private static int callInCustomerTime;
    // Stores the question time length of the next customer created.
    private static int questionTimeLength;
    // Stores a random number between one and the size of the names database.
    private static int nameDatabaseSize;
    // Stores the random number to pull from the names database.
    private static String randomCustomerIDString;
    /**
     * The Timer constructor begins the Timer simulation that will run a total
     * of the integer argument received.
     * @param totalTime is the length of the simulated time.
     * @param walkInMean the mean time of a new walk-in customer.
     * @param callInMean the mean time of a new call-in customer.
     * @param questionMean the mean time of the new customers question time.
     * @param results results of the simulation.
     */
    public Timer(int totalTime, int questionMean, int walkInMean,
            int callInMean, JTextArea results) {
        // Timer mean times.
        final int WALK_IN_MEAN = walkInMean;
        final int CALL_IN_MEAN = callInMean;
        final int QUESTION_MEAN = questionMean;
        // Customer priority levels.
        final int WALK_IN_CUSTOMER_PRIORITY = 0;
        final int CALL_IN_CUSTOMER_PRIORITY = 1;
        // Class objects.
        Queue hybridQueue = new Queue();
        LogDB logDB = new LogDB();
        // The number at which the customer was placed in the queue.
        int customerEnqueueID = 0;

        //====== Start connection to the GeneratedCustomers database =====
        
        Connection connect;
        Statement statement;
        ResultSet resultSet;

        //======== Code that runs this program from different locations ===
        
       // This runs in IDE's that do not require a specified path like InteliJ.
//       try {
//           Class.forName("org.sqlite.JDBC");
//
//           connect = DriverManager.getConnection("jdbc:sqlite:CustomerDB.sql");
//           statement = connect.createStatement();
//           statement.setQueryTimeout(30);
//           // Get total row count on database.
//           resultSet = statement.executeQuery(
//                   "SELECT count(*) AS total FROM GeneratedCustomers");
//           // Store total row count into class variable.
//           nameDatabaseSize = resultSet.getInt("total");
//       }
        
        // To run from any IDE.
//        String databasePath = getClass().getProtectionDomain().getCodeSource()
//                .getLocation().getPath() + "CustomerDB.sql";
        
        // To run from JAR.
        String databasePath = System.getProperty("user.dir")
                + "/Databases/CustomerDB.sql";

        // This runs in IDE's that require a specified path like Netbeans.
        try {
            Class.forName("org.sqlite.JDBC");

            connect = DriverManager.getConnection(
                    "jdbc:sqlite:" + databasePath);

            statement = connect.createStatement();
            statement.setQueryTimeout(30);
            // Get total row count on database.
            resultSet = statement.executeQuery(
                    "SELECT count(*) AS total FROM GeneratedCustomers");
            // Store total row count into class variable.
            nameDatabaseSize = resultSet.getInt("total");
        }
        catch(ClassNotFoundException | SQLException ex0) {
            System.err.println(ex0.getMessage());
        }
        
        // Clears any previous log tables information from the customer log.
        logDB.clearLog();
        
        // First walk-in customer.
        walkInCustomerTime = poissonRandom(WALK_IN_MEAN);
        // First call-in customer.
        callInCustomerTime = poissonRandom(CALL_IN_MEAN);
        // Generates customer ID and question time length.
        randomCustomerGenerator(QUESTION_MEAN);

        //====================== Start simulated time ====================
        
        for(int i = 0; i < totalTime; i++) {
            try{
                // Dequeue head if finish time equals current time(i).
                if(hybridQueue.getHead() != null){
                    if(i == Integer.parseInt(
                            hybridQueue.getHead().getFinishTime())) {
                        // Records customer information when dequeued.
                        Customer tempCustomer = hybridQueue.removeFromQueue();
                        tempCustomer.setQuestionAnswered("YES");
                        logDB.enterNewLog(tempCustomer, hybridQueue.size());
                        results.append(tempCustomer.getFirstName() + " " +
                                tempCustomer.getLastName() + ", customer " +
                                tempCustomer.getQueueID() +
                                ", got her question answered at " + i +
                                " seconds. " + hybridQueue.size() +
                                " remain in line.\n");
                    }  // if question answered
                }
            }
            catch (NullPointerException npe) { /* Do Nothing */ }

            // If it's time for a customer to walk in.
            if(i == walkInCustomerTime) {
                // Increase customer enqueue ID number.
                ++customerEnqueueID;
                // Create the customer and set the customer values.
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
                customer.setQuestionTime(questionTimeLength);
                customer.setCustomerType("Walk-In");
                  // If the queue is empty, the finish time are not modified.
                if(hybridQueue.isEmpty()) {
                  customer.setFinishTime(i + questionTimeLength);
                }
                  // If the queue is not empty, the finish time is equal to the
                  // the customer aheads finish time + current question time.
                else {
                    Customer tempCustomer = hybridQueue.getTail();
                    customer.setFinishTime(Integer.parseInt(
                            tempCustomer.getFinishTime()) + questionTimeLength);
                }
                hybridQueue.addToQueue(customer, WALK_IN_CUSTOMER_PRIORITY);
                // Set next walk-in customer time.
                walkInCustomerTime += poissonRandom(WALK_IN_MEAN);
                // Set next customers random ID and question length.
                randomCustomerGenerator(QUESTION_MEAN);

                // display this event in the GUI
                results.append(customer.getFirstName() + " " +
                        customer.getLastName() + ", customer " +
                        customer.getQueueID() + ", walked in at " + i +
                        " seconds. " + hybridQueue.size() +" are in line.\n");
                //results.repaint();
            }
            // If it's time for a customer to call in.
            if(i == callInCustomerTime) {
                ++customerEnqueueID;
                // Create the customer and set the customer values.
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
                customer.setCustomerType("Call-In");
                // Call-in customer never gets modified times because they jump
                // to the head of the queue.
                customer.setQuestionTime(questionTimeLength);
                customer.setFinishTime(i + questionTimeLength);
                hybridQueue.addToQueue(customer, CALL_IN_CUSTOMER_PRIORITY);
                // Set next call-in customer time.
                callInCustomerTime += poissonRandom(CALL_IN_MEAN);
                // Set next customers random ID and question length.
                randomCustomerGenerator(QUESTION_MEAN);

                // Display this event in the GUI
                results.append(customer.getFirstName() + " " +
                        customer.getLastName() + ", customer " +
                        customer.getQueueID() + ", called at " + i +
                        " seconds. " + hybridQueue.size() +" are in line.\n");
                results.repaint();
            }
        }  // primary for loop.
        // Remove remaining customers from line after timer has completed.
        if(hybridQueue.size() > 0){
            while(!hybridQueue.isEmpty()) {
                Customer tempCustomer = hybridQueue.removeFromQueue();
                tempCustomer.setQuestionAnswered("No");
                logDB.enterNewLog(tempCustomer, hybridQueue.size());
            }
        }
    }
    /**
     * randomCustomerGenerator generates a random integer that is passed through
     * to the Customer class, that is used to pull a random customers
     * information from the GeneratedCustomers database.  The number limit is
     * determined by the number or rows contained in the database, which is
     * retrieved in the above connection to the GeneratedCustomers database.
     * @param questionMean the mean time of all questions being asked.
     */
    public void randomCustomerGenerator(int questionMean) {
        // Stores the random number to pull from the names database.
        int randomCustomerID;
        
        randomCustomerID = (int)(Math.random() * nameDatabaseSize) + 1;
        randomCustomerIDString = Integer.toString(randomCustomerID);
        questionTimeLength = poissonRandom(questionMean);
    }
    /**
     * poissonRandom generates a random number based on the mean that is passed
     * through as an argument.
     * @param mean the mean of the random number generated.
     * @return the random number generated.
     */
    public int poissonRandom(int mean) {
        double randomNum;
        double poissonRandomNum;
        
        randomNum = Math.random();
        poissonRandomNum = (mean * -Math.log(randomNum)) + 1;

        return (int)poissonRandomNum;
    }
}
