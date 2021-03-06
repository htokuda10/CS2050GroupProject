package groupassignment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
/**
 * The LogDB will log all of the customer information after the customer has
 * been dequeued.  The LogDB also contains the method that will clear any
 * previous logs in order to prevent overlapping any previously logged
 * information.
 * @author Hideyuki Tokuda, Jason Mathews, Jedediah Hernandez
 */
public class LogDB {
    
    /**
     * clearLog clears the database of any previous customer logs.
     */
    public void clearLog() {
        
        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;
        
        //======== Code that runs this program from different locations ===
        
        // This runs in IDE's that do not require a specified path like InteliJ.
//        try {
//            // Import sqlite library.
//            Class.forName("org.sqlite.JDBC");
//            // Connect to database.
//            connect = DriverManager.getConnection("jdbc:sqlite:LogDB.sql");
//            statement = connect.createStatement();
//            statement.setQueryTimeout(30);
//            statement.executeUpdate("drop table if exists customerLog");
//            statement.executeUpdate("create table customerLog ("
//                    + "queueId Integer, customerID Integer, firstName String,"
//                    + " lastName String, customerType String,"
//                    + " questionAnswered String, startTime Integer,"
//                    + " finishTime Integer, questionTime Integer,"
//                    + " remainingQueue)");
//        }
        
        // To run from any IDE.
//        String databasePath = getClass().getProtectionDomain().getCodeSource()
//                .getLocation().getPath() + "LogDB.sql";
        
        // To run from JAR.
        String databasePath = System.getProperty("user.dir")
                + "/Databases/LogDB.sql";        

        // This runs in IDE's that require a specified path like Netbeans.
        try {
            // Import sqlite library.
            Class.forName("org.sqlite.JDBC");
            // Connect to database.
            connect = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            statement = connect.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists customerLog");
            statement.executeUpdate("create table customerLog ("
                    + "queueId Integer, customerID Integer, firstName String,"
                    + " lastName String, customerType String,"
                    + " questionAnswered String, startTime Integer,"
                    + " finishTime Integer, questionTime Integer,"
                    + " remainingQueue)");
        }
        catch(ClassNotFoundException | SQLException ex0) {
            System.err.println(ex0.getMessage());
        }
        // Close connections.
        finally {
            if(connect != null){
                try {
                    connect.close();
                }
                catch(SQLException sqlex) {
                    // Ignore.
                }
            }
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException sqlex) {
                    // Ignore.
                }
            }
        } // finally
    } // clearLog
    /**
     * enterNewLog adds new customer log to the log database after they have
     * completely been serviced (dequeued).
     * @param info contains all of the customers information.
     * @param queueSize remaining customers in queue.
     */
    public void enterNewLog(Customer info, int queueSize) {

        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;
        
        //======== Code that runs this program from different locations ===
        
        // This runs in IDE's that do not require a specified path like InteliJ.
//        try {
//            Class.forName("org.sqlite.JDBC");
//
//            connect = DriverManager.getConnection("jdbc:sqlite:LogDB.sql");
//            statement = connect.createStatement();
//            statement.setQueryTimeout(30);
//
//            statement.executeUpdate("insert into CustomerLog values("
//                    + info.getQueueID() + ", "
//                    + info.getCustomerID() + ", "
//                    + "'" + info.getFirstName() + "'" + ", "
//                    + "'" + info.getLastName() + "'" + ", "
//                    + "'" + info.getCustomerType() + "'" + ", "
//                    + "'" + info.getQuestionAnswered() + "'" + ", "
//                    + info.getCreationTime() + ", "
//                    + info.getFinishTime() + ", "
//                    + info.getQuestionTime() + ", "
//                    + Integer.toString(queueSize) + ")");
//        }
        
        // To run from any IDE.
//        String databasePath = getClass().getProtectionDomain().getCodeSource()
//                .getLocation().getPath() + "LogDB.sql";
        
        // To run from JAR.
        String databasePath = System.getProperty("user.dir")
                + "/Databases/LogDB.sql";

        // This runs in IDE's that require a specified path like Netbeans.
        try {
            Class.forName("org.sqlite.JDBC");

            connect = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
            statement = connect.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("insert into CustomerLog values("
                    + info.getQueueID() + ", "
                    + info.getCustomerID() + ", "
                    + "'" + info.getFirstName() + "'" + ", "
                    + "'" + info.getLastName() + "'" + ", "
                    + "'" + info.getCustomerType() + "'" + ", "
                    + "'" + info.getQuestionAnswered() + "'" + ", "
                    + info.getCreationTime() + ", "
                    + info.getFinishTime() + ", "
                    + info.getQuestionTime() + ", "
                    + Integer.toString(queueSize) + ")");
        }
        catch(ClassNotFoundException | SQLException ex0) {
            System.err.println(ex0.getMessage());
        }
        finally {
            if(connect != null) {
                try {
                    connect.close();
                }
                catch(SQLException sqlex) {
                    // Do nothing.
                }
            }
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException sqlex) {
                    // Do nothing.
                }
            }
        } // finally
    } // enterNewLog
} // class