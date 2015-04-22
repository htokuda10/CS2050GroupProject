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
    final static String CURRENT_PATH = LogDB.class.getProtectionDomain()
            .getCodeSource().getLocation().getPath();
    /**
     * clearLog clears the database of any previous customer logs.
     */
    public static void clearLog() {
        
        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            
            connect = DriverManager.getConnection("jdbc:sqlite:" + CURRENT_PATH
                + "LogDB.sql");
            statement = connect.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists customerLog");
            statement.executeUpdate("create table customerLog ("
                    + "queueId Integer, customerID Integer, firstName String,"
                    + " lastName String, startTime Integer, finishTime Integer,"
                    + " questionTime Integer)");
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
     * @param info 
     */
    public static void enterNewLog(Customer info) {
        
        //============= Start connection with the log database ==========
        
        Connection connect = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            
            connect = DriverManager.getConnection("jdbc:sqlite:" + CURRENT_PATH
                    + "LogDB.sql");
            statement = connect.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("insert into CustomerLog values("
                    + info.getQueueID() + ", "
                    + info.getCustomerID() + ", "
                    + "'" + info.getFirstName() + "'" + ", "
                    + "'" + info.getLastName() + "'" + ", "
                    + info.getCreationTime() + ", "
                    + info.getFinishTime() + ", "
                    + info.getQuestionTime() + ")");
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
