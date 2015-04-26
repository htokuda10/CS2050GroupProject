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
     * @param walkInMean
     * @param callInMean
     * @param questionMean
     */
    public Timer(
            int totalTime, int walkInMean, int callInMean, int questionMean) {
        // Customer priority levels.
        final int WALK_IN_MEAN = walkInMean;
        final int CALL_IN_MEAN = callInMean;
        final int QUESTION_MEAN = questionMean;
        final int WALK_IN_CUSTOMER_PRIORITY = 0;
        final int CALL_IN_CUSTOMER_PRIORITY = 1;
        final int YES = 0;
        final int NO = 1;
        Queue hybridQueue = new Queue();
        // The number at which the customer was placed in the queue.
        int customerEnqueueID = 0;

        //====== Start connection to the GeneratedCustomers database =====
        
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        String path = Timer.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
                                                                                // Use this to run on InteliJ.
                                                                                try {
                                                                                    Class.forName("org.sqlite.JDBC");

                                                                                    connect = DriverManager.getConnection(
                                                                                            "jdbc:sqlite:CustomerDB.sql");
                                                                                    statement = connect.createStatement();
                                                                                    statement.setQueryTimeout(30);
                                                                                    // Get total row count on database.
                                                                                    resultSet = statement.executeQuery(
                                                                                            "SELECT count(*) AS total FROM GeneratedCustomers");
                                                                                    // Store total row count into class variable.
                                                                                    nameDatabaseSize = resultSet.getInt("total");
                                                                                }
        
        // This block includes "path".
//        try {
//            Class.forName("org.sqlite.JDBC");
//        
//            connect = DriverManager.getConnection(
//                    "jdbc:sqlite:" + path + "CustomerDB.sql");
//            statement = connect.createStatement();
//            statement.setQueryTimeout(30);
//            // Get total row count on database.
//            resultSet = statement.executeQuery(
//                    "SELECT count(*) AS total FROM GeneratedCustomers");
//            // Store total row count into class variable.
//            nameDatabaseSize = resultSet.getInt("total");
//        }
        catch(ClassNotFoundException | SQLException ex0) {
            System.err.println(ex0.getMessage());
        }
        
        // Clears any previous log tables information from the customer log.
        LogDB.clearLog();
        
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
                // This prevents a finished customer from being pushed back.
                if(hybridQueue.getHead() != null){
                    if(i == Integer.parseInt(hybridQueue.getHead().getFinishTime())) {
                        Customer tempCustomer = hybridQueue.removeFromQueue();
                        tempCustomer.setQuestionAnswered("YES");
                        LogDB.enterNewLog(tempCustomer, hybridQueue.size());
                    }
                }
            }
            catch(NullPointerException ex0){
                // Do Nothing.
            }
            // If it's time for a customer to walk in.
            if(i >= walkInCustomerTime) {
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
            }
            // If it's time for a customer to call in.
            if(i >= callInCustomerTime) {
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
            }
        }// primary for loop.
        if(hybridQueue.size() > 0){
            for(int i = 0; i < hybridQueue.size(); i++) {
                Customer tempCustomer = hybridQueue.removeFromQueue();
                tempCustomer.setQuestionAnswered("No");
                LogDB.enterNewLog(tempCustomer, hybridQueue.size());
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
        poissonRandomNum = mean * -Math.log(randomNum);

        return (int)poissonRandomNum;
    }
}
