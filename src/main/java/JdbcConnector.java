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


    public static Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName(DB_DRIVER).newInstance();
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        return connection;
    }
}
