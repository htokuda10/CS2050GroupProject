/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupassignment;
import java.sql.*;

/**
 *
 * @author Yuki
 */
public class Timer {
        private static int walkInCustomerTime;
        private static int callInCustomerTime;
        private static int questionTimeLength;
        private static int randomCustomerID;
        private static int nameDatabaseSize;
        private static String randomCustomerIDString;
        
    public Timer(int totalTime){
        final int WALK_IN_CUSTOMER_PRIORITY = 0;
        final int CALL_IN_CUSTOMER_PRIORITY = 1;
                                                                                // Customer tempCustomerForInfo; ToDo - when dequeing,
                                                                                // store here then pass to the LogDB class for logging.
        int customerEnqueueID = 0;

        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        String path = Timer.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        
        try{
            Class.forName("org.sqlite.JDBC");
        
            connect = DriverManager.getConnection(
                    "jdbc:sqlite:" + path + "CustomerDB.sql");
            statement = connect.createStatement();
            statement.setQueryTimeout(30);
            
            resultSet = statement.executeQuery(
                    "SELECT count(*) AS total FROM GeneratedCustomers");
            
            nameDatabaseSize = resultSet.getInt("total");
        }
        catch(ClassNotFoundException | SQLException ex0){
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

        for(int i = 0; i < totalTime; i++){
            if(i >= walkInCustomerTime){
                ++customerEnqueueID;
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
                customer.setQuestionTime(questionTimeLength);
                                                                                //if(HybridQueue.isEmpty()){
                                                                                //  customer.setFinishTime(i + questionTimeLength);
                                                                                //}
                                                                                //else{
                                                                                //  customer.setFinishTime(HybridQueue.getTail().getFinishTime() + questionTimeLength);
                                                                                //}
                customer.setFinishTime(i + questionTimeLength);
                System.out.println("Walked in: " + customer.getFirstName()
                        + " " + customer.getLastName() + " at " + i);
                                                                                // HybridQueue.push(customer, WALK_IN_CUSTOMER_PRIORITY); ToDo
                                                                                LogDB.enterNewLog(customer); // Test log system.
                // Set next walk-in customer time.
                walkInCustomerTime += poissonRandom(45) + questionTimeLength;
                // Set next customers random ID and question length.
                randomCustomerGenerator();
            }
            if(i >= callInCustomerTime){
                ++customerEnqueueID;
                Customer customer = new Customer(randomCustomerIDString);
                customer.setQueueID(customerEnqueueID);
                customer.setCreationTime(i);
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
                                                                                if(i == HybridQueue.getCurrent().getFinishTime()){
                                                                                    tempCustomerForInfo = HybridQueue.dequeue();
                                                                                }
                                                                                */
        }
    }
    
    public void randomCustomerGenerator(){
        randomCustomerID = (int)(Math.random() * nameDatabaseSize) + 1;
        randomCustomerIDString = Integer.toString(randomCustomerID);
        questionTimeLength = poissonRandom(25);
    }
    
    public int poissonRandom(int mean){
        double randomNum;
        double poissonRandomNum;
        
        randomNum = Math.random();
        poissonRandomNum = mean * -Math.log(randomNum);

        return (int)poissonRandomNum;
    }
                                                                                /*
                                                                                public void logDB(tempCustomerForInfo){
                                                                                    // Retrieve all customer data and log information. ToDo
                                                                                }
                                                                                */
}
