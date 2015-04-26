package groupassignment;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * The Customer class builds a new customer object that will contain all of the
 * information pertaining to that customer.
 * @author Yuki
 */
public final class Customer {
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
     * @param randomID the customer information location within the database.
     */
    public Customer(){
        
    }
    
    public Customer(String randomID) {
        
        //======= Start connection to the GeneratedCustomers database =====
        
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        String path = Customer.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
                                                                                // Use this to run on InteliJ.
                                                                                try {
                                                                                    Class.forName("org.sqlite.JDBC");
                                                                                    connect = DriverManager.getConnection(
                                                                                            "jdbc:sqlite:CustomerDB.sql");
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
        // This block includes "path".
//        try {
//            Class.forName("org.sqlite.JDBC");
//            connect = DriverManager.getConnection(
//                    "jdbc:sqlite:" + path + "CustomerDB.sql");
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
    
    // Constructor with known variables.
    public Customer(int creationTime, int questionTime, int customerID,
            String firstName, String lastName) {
        this.creationTime = creationTime;
        this.finishTime = creationTime + questionTime;
        this.questionTime = questionTime;
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Updates the finish times for the customers.
    public void updateTime(int addTime) {
        finishTime += addTime;
    }
    // Set initial position in queue.
    public void setQueueID(int queueID) {
        this.queueID = queueID;
    }
    // Get initial position in queue.
    public String getQueueID() {
        return Integer.toString(queueID);
    }
    // Set the individual customer ID.
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    // Get the individual customers ID.
    public String getCustomerID() {
        return Integer.toString(customerID);
    }
    // Set the time the customer gets into queue.
    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }
    // Get the time the customer gets into queue.
    public String getCreationTime() {
        return Integer.toString(creationTime);
    }
    // Set the time the customer will be finished asking a question.
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
    // Get the time the customer will be finished asking a question.
    public String getFinishTime() {
        return Integer.toString(this.finishTime);
    }
    // Set the amount of time it will take to answer the customers question.
    public void setQuestionTime(int questionTime) {
        this.questionTime = questionTime;
    }
    // Get the amount of time it will take to answer the customers question.
    public String getQuestionTime() {
        return Integer.toString(questionTime);
    }
    // Set the time the customer was in queue, for the record.
    public void setTotalQueueTime(int queueTime) {
        this.totalQueueTime = queueTime;
    }
    // Get the time the customer was in queue, for the record.
    public String getTotalQueueTime() {
        return Integer.toString(totalQueueTime);
    }
    // Set queue type(walk-in or call-in).
    public void setCustomerType(String queueType){
        this.customerType = queueType;
    }
    // Get queue type(walk-in or call-in).
    public String getCustomerType(){
        return customerType;
    }
    // Set question answered.
    public void setQuestionAnswered(String answered) {
        this.questionAnswered = answered;
    }
    // Get question answered.
    public String getQuestionAnswered(){
        return questionAnswered;
    }
    // Get the customers first anem, for the record.
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    // Get the customers first anem, for the record.
    public String getFirstName() {
        return firstName;
    }
    // Get the customers last name, for the record.
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    // Get the customers last name, for the record.
    public String getLastName() {
        return lastName;
    }
} // class