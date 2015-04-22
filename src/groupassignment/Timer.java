package groupassignment;
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
 * after the customer has reached it's finish time, which is determined by the 
 * question time and the time the customer reached the head of the queue.
 * @author Yuki
 */
public class Timer {
        private static int walkInCustomerTime;
        private static int callInCustomerTime;
        private static int questionTimeLength;
        private static int randomCustomerID;
        private static int nameDatabaseSize;
        private static String randomCustomerIDString;
    /**
     * The Timer constructor begins the Timer simulation that will run a total
     * of the integer argument received.
     * @param totalTime is the length of the simulated time.
     */
    public Timer(int totalTime) {
        // Customer priority levels.
        final int WALK_IN_CUSTOMER_PRIORITY = 0;
        final int CALL_IN_CUSTOMER_PRIORITY = 1;
                                                                                // Customer tempCustomerForInfo; ToDo - when dequeing,
                                                                                // store here then pass to the LogDB class for logging.
        // The number at which the customer was placed in the queue.
        int customerEnqueueID = 0;

        //====== Start connection to the GeneratedCustomers database =====
        
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        String path = Timer.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        
        try {
            Class.forName("org.sqlite.JDBC");
        
            connect = DriverManager.getConnection(
                    "jdbc:sqlite:" + path + "CustomerDB.sql");
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
        LogDB.clearLog();
        
        // First walk-in customer.
        walkInCustomerTime = poissonRandom(45);
        // First call-in customer.
        callInCustomerTime = poissonRandom(55);
        // Generates customer ID and question time length.
        randomCustomerGenerator();

        //====================== Start simulated time ====================
        
        for(int i = 0; i < totalTime; i++) {
            // If it's time for a customer to walk in.
            if(i >= walkInCustomerTime) {
                ++customerEnqueueID;
                // Create the customer and set the customer values.
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
                customer.setQuestionTime(questionTimeLength);
                  // If the queue is empty, the finish time are not modified.
//                if(HybridQueue.isEmpty()) {
//                  customer.setFinishTime(i + questionTimeLength);
//                }
                  // If the queue is not empty, the finish time is equal to the
                  // the customer aheads finish time + current question time.
//                else {
//                  customer.setFinishTime(HybridQueue.getTail().getFinishTime() + questionTimeLength);
//                }
                customer.setFinishTime(i + questionTimeLength); // Remove this when queue is built.  This is temporary.
                                                                                System.out.println("Walked in: " + customer.getFirstName()
                                                                                        + " " + customer.getLastName() + " at " + i);
                                                                                // HybridQueue.push(customer, WALK_IN_CUSTOMER_PRIORITY); ToDo
                                                                                LogDB.enterNewLog(customer); // Test log system.
                // Set next walk-in customer time.
                walkInCustomerTime += poissonRandom(45) + questionTimeLength;
                // Set next customers random ID and question length.
                randomCustomerGenerator();
            }
            // If it's time for a customer to call in.
            if(i >= callInCustomerTime) {
                ++customerEnqueueID;
                // Create the customer and set the customer values.
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
                // Call-in customer never gets modified times because they jump
                // to the head of the queue.
                customer.setQuestionTime(questionTimeLength);
                customer.setFinishTime(i + questionTimeLength);
                                                                                System.out.println("Called in: " + customer.getFirstName()
                                                                                        + " " + customer.getLastName() + " at " + i);
                                                                                // HybridQueue.push(customer, CALL_IN_CUSTOMER_PRIORITY); ToDo
                                                                                // HybridQueue.updateTimes(questionTimeLength); ToDo
                                                                                LogDB.enterNewLog(customer); // Test customer log system.
                // Set next call-in customer time.
                callInCustomerTime += poissonRandom(55) + questionTimeLength;
                // Set next customers random ID and question length.
                randomCustomerGenerator();
            }
                                                                                /*
                                                                                if(i == HybridQueue.getCurrent().getFinishTime()) {
                                                                                    tempCustomerForInfo = HybridQueue.dequeue();
                                                                                }
                                                                                */
        }
    }
    /**
     * randomCustomerGenerator generates a random integer that is passed through
     * to the Customer class, that is used to pull a random customers
     * information from the GeneratedCustomers database.  The number limit is
     * determined by the number or rows contained in the database, which is
     * retrieved in the above connection to the GeneratedCustomers database.
     */
    public void randomCustomerGenerator() {
        randomCustomerID = (int)(Math.random() * nameDatabaseSize) + 1;
        randomCustomerIDString = Integer.toString(randomCustomerID);
        questionTimeLength = poissonRandom(25);
    }
    /**
     * poissonRandom generates a random number based on the mean that is passed
     * through as an arguement.
     * @param mean the mean of the random number generated.
     * @return the random number generated.
     */
    public int poissonRandom(int mean) {
        double randomNum;
        double poissonRandomNum;
        
        randomNum = Math.random();
        poissonRandomNum = mean * -Math.log(randomNum);

        return (int)poissonRandomNum;
    }
                                                                                /*
                                                                                public void logDB(tempCustomerForInfo) {
                                                                                    // Retrieve all customer data and log information. ToDo
                                                                                }
                                                                                */
}
