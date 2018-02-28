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

    public static Connection connect() {
        try {
            Class.forName(DB_DRIVER);   //A co z newInstance() ? z newInstance() wyrzuca że nie obsłużone wyjątki ale bez newInstance() działa.
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
                System.out.println("\n" + "Connected to the database successfully." + "\n");
            } catch (SQLException ex) {
                System.out.println("\n" + "Failed to create a database connection." + "\n");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("\n" + "Driver class not found." + "\n");
        }
        return connection;
    }

    public static void disconnect() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
            if (connection.isClosed()) {
                System.out.println("\n" + "Connection to the database is closed." + "\n");
            } else {
                throw new RuntimeException("\n" + "Failed to disconnect from the database." + "\n");
            }
        }
    }
}