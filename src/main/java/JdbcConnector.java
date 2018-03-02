import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Created by Piotr on 26.02.2018.
 */
public class JdbcConnector {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/shopdb";
    private final static String DB_USER_NAME = "root";
    private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_PASSWORD = "MySQLPassword";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
            try {
                Class.forName(DB_DRIVER);
                System.out.println("\n" + "Connected to the database successfully." + "\n");
            } catch (ClassNotFoundException ex) {
                System.out.println("\n" + "Driver class not found." + "\n");
            }
        } catch (SQLException ex) {
            System.out.println("\n" + "Failed to create a database connection." + "\n");
        }
    }


    public static Connection getCconnection() {
        return connection;
    }
}