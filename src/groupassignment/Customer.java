package groupassignment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The Customer class builds a new customer object that will contain all of the
 * information pertaining to that customer.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public final class Customer {
    // Customer data.
    private int creationTime;
    private int finishTime;
    private int questionTime;
    private int totalQueueTime;
    private int queueID;
    private int customerID;
    private String firstName;
    private String lastName;
    private String customerType;
    private String questionAnswered;
    /**
     * This constructor will build a new customer and define three variables,
     * customer ID, first name and last name.  The random variables will be
     * generated from the CustomerDB.sql database.
     * @param randomID the unique ID within the database.
     */
    public Customer(String randomID) {
        
        //======= Start connection to the GeneratedCustomers database =====
        
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //======== Code that runs this program from different locations ===
        
        // This runs in IDE's that do not require a specified path like InteliJ.
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connect = DriverManager.getConnection(
//                    "jdbc:sqlite:CustomerDB.sql");
//            statement = connect.createStatement();
//            statement.setQueryTimeout(30);
//            resultSet = statement.executeQuery(
//                    "select * from GeneratedCustomers where customerID="
//                        + randomID);
//            // Store retrieved data values into currently created customer.
//            customerID = resultSet.getInt("customerID");
//            firstName = resultSet.getString("firstName");
//            lastName = resultSet.getString("lastName");
//        }
        
                // To run from any IDE.
//        String databasePath = getClass().getProtectionDomain().getCodeSource()
//                .getLocation().getPath() + "CustomerDB.sql";
        
        // To run from JAR.
        String databasePath = System.getProperty("user.dir")
                + "/Databases/CustomerDB.sql";
        
        // This runs in IDE's that require a specified path like Netbeans.
        try {
            // Import sqlite library.
            Class.forName("org.sqlite.JDBC");
            // Connect to database.
            connect = DriverManager.getConnection(
                    "jdbc:sqlite:" + databasePath);
            statement = connect.createStatement();
            statement.setQueryTimeout(30);
            resultSet = statement.executeQuery(
                    "select * from GeneratedCustomers where customerID="
                        + randomID);
            // Store retrieved data values into currently created customer.
            customerID = resultSet.getInt("customerID");
            firstName = resultSet.getString("firstName");
            lastName = resultSet.getString("lastName");
        }
        catch(ClassNotFoundException | SQLException ex0) {
            System.err.println(ex0.getMessage());
        }
        finally {
            if(connect != null) {
                try{
                    connect.close();
                }
                catch(SQLException sqlex) {
                    // Ignore
                }
            }
            if(statement != null) {
                try{
                    statement.close();
                }
                catch(SQLException sqlex) {
                    // Ignore
                }
            }
            if(resultSet != null) {
                try{
                    resultSet.close();
                }
                catch(SQLException sqlex) {
                    // Ignore
                }
            }
        } // finally
    } // customer constructor
    
    /**
     * This constructor will build a new customer using parameters to define the
     * variable data.
     * @param creationTime defines the creation time.
     * @param questionTime defines the question time.
     * @param customerID defines the unique customer ID.
     * @param firstName defines the first name of the customer.
     * @param lastName defines the last name of the customer.
     */
    public Customer(int creationTime, int questionTime, int customerID,
            String firstName, String lastName) {
        // Assign all argument values to associated variables.
        this.creationTime = creationTime;
        this.finishTime = creationTime + questionTime;
        this.questionTime = questionTime;
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * updateTime updates the finish time of this customer by the amount passed
     * as an argument.
     * @param addTime amount of time to increase the current customers finish
     * time.
     */
    public void updateTime(int addTime) {
        finishTime += addTime;
    }
    /**
     * setQueueID sets this customers enqueue ID number.
     * @param queueID enqueue ID number.
     */
    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }
    /**
     * getQueueID gets this customers enqueue ID number.
     * @return this customers enqueue ID number.
     */
    public String getQueueID() {
        return Integer.toString(queueID);
    }
    /**
     * setCustomerID sets this customers unique ID number.
     * @param customerID unique ID number.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /**
     * getCustomerID gets this customers unique ID number.
     * @return unique ID number.
     */
    public String getCustomerID() {
        return Integer.toString(customerID);
    }
    /**
     * setCreationTime sets this customers creation time.
     * @param creationTime creation time.
     */
    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }
    /**
     * getCreationTime gets this customers creation time.
     * @return creation time.
     */
    public String getCreationTime() {
        return Integer.toString(creationTime);
    }
    /**
     * setFinishTime sets this customers finish time.
     * @param finishTime finish time.
     */
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
    /**
     * getFinishTime gets this customers finish time.
     * @return finish time.
     */
    public String getFinishTime() {
        return Integer.toString(this.finishTime);
    }
    /**
     * setQuestionTime sets the length of this customers question time.
     * @param questionTime question length time.
     */
    public void setQuestionTime(int questionTime) {
        this.questionTime = questionTime;
    }
    /**
     * getQuestionTime gets this customers question length time.
     * @return question length time.
     */
    public String getQuestionTime() {
        return Integer.toString(questionTime);
    }
    /**
     * setTotalQueueTime sets this customers total time in queue.
     * @param queueTime total time in queue.
     */
    public void setTotalQueueTime(int queueTime) {
        this.totalQueueTime = queueTime;
    }
    /**
     * getTotalQueueTime gets this customers total time in queue.
     * @return total time in queue.
     */
    public String getTotalQueueTime() {
        return Integer.toString(totalQueueTime);
    }
    /**
     * setCustomerType sets this customer type as walk-in or call-in.
     * @param queueType 'walk-in' or 'call-in'.
     */
    public void setCustomerType(String queueType){
        this.customerType = queueType;
    }
    /**
     * getCustomerType gets this customer type.
     * @return customer type, walk-in or call-in.
     */
    public String getCustomerType(){
        return customerType;
    }
    /**
     * setQuestionAnswer sets if this customers question was answered to 'yes' 
     * or 'no'.
     * @param answered 'yes' or 'no'.
     */
    public void setQuestionAnswered(String answered) {
        this.questionAnswered = answered;
    }
    /**
     * getQuestionAnswered gets the answer to if this customers question was
     * answered.
     * @return 'yes' or 'no'.
     */
    public String getQuestionAnswered(){
        return questionAnswered;
    }
    /**
     * setFristName sets this customers first name.
     * @param firstName first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * getFirstName gets this customers first name.
     * @return first name.
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * setLastName sets this customers last name.
     * @param lastName last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * getLastName gets this customers last name.
     * @return last name.
     */
    public String getLastName() {
        return lastName;
    }
} // class