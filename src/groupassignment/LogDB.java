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
 * @author Yuki
 */
public class LogDB {
    final static String PATH = LogDB.class.getProtectionDomain()
            .getCodeSource().getLocation().getPath();
    /**
     * clearLog clears the database of any previous customer logs.
     */
    public static void clearLog() {
        
        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;
                                                                                // Use this for InteliJ.
//                                                                                try {
//                                                                                    Class.forName("org.sqlite.JDBC");
//
//                                                                                    connect = DriverManager.getConnection("jdbc:sqlite:LogDB.sql");
//                                                                                    statement = connect.createStatement();
//                                                                                    statement.setQueryTimeout(30);
//                                                                                    statement.executeUpdate("drop table if exists customerLog");
//                                                                                    statement.executeUpdate("create table customerLog ("
//                                                                                            + "queueId Integer, customerID Integer, firstName String,"
//                                                                                            + " lastName String, customerType String,"
//                                                                                            + " questionAnswered String, startTime Integer,"
//                                                                                            + " finishTime Integer, questionTime Integer,"
//                                                                                            + " remainingQueue)");
//                                                                                }
        
        // This block includes "PATH".
        try {
            Class.forName("org.sqlite.JDBC");
            
            connect = DriverManager.getConnection("jdbc:sqlite:" + PATH
                + "LogDB.sql");
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
    public static void enterNewLog(Customer info, int queueSize) {

        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;
                                                                                // Use this for InteliJ.
//                                                                                try {
//                                                                                    Class.forName("org.sqlite.JDBC");
//
//                                                                                    connect = DriverManager.getConnection("jdbc:sqlite:LogDB.sql");
//                                                                                    statement = connect.createStatement();
//                                                                                    statement.setQueryTimeout(30);
//
//                                                                                    statement.executeUpdate("insert into CustomerLog values("
//                                                                                            + info.getQueueID() + ", "
//                                                                                            + info.getCustomerID() + ", "
//                                                                                            + "'" + info.getFirstName() + "'" + ", "
//                                                                                            + "'" + info.getLastName() + "'" + ", "
//                                                                                            + "'" + info.getCustomerType() + "'" + ", "
//                                                                                            + "'" + info.getQuestionAnswered() + "'" + ", "
//                                                                                            + info.getCreationTime() + ", "
//                                                                                            + info.getFinishTime() + ", "
//                                                                                            + info.getQuestionTime() + ", "
//                                                                                            + Integer.toString(queueSize) + ")");
//                                                                                }
        
        // This block includes "PATH".
        try {
            Class.forName("org.sqlite.JDBC");
            
            connect = DriverManager.getConnection("jdbc:sqlite:" + PATH
                    + "LogDB.sql");
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
                    // Ignore
                }
            }
            if(statement != null) {
                try {
                    statement.close();
                }
                catch(SQLException sqlex) {
                    // Ignore
                }
            }
        } // finally
    } // enterNewLog
} // class
