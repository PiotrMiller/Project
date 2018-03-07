import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Piotr on 07.03.2018.
 */
public class OrdersDao {

    public Order createNewOrder(int customerId) throws SQLException {
        Order order = new Order(customerId);
        Connection connection = JdbcConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (customer_id) values (?)"))
        {
            if (CustomersDao.isCustomerExist(customerId)) {
                preparedStatement.setInt(1, customerId);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int autoIncrementOrderId = resultSet.getInt(1);
                order.setOrderId(autoIncrementOrderId);
                System.out.println("New order is successfully added to the database");
                resultSet.close();
                return order;
            }
            return null;
        }
    }

    private PreparedStatement createPreparedStatementforGetOrder(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT order_id, customer_id From orders WHERE order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public Order getOrder(int id) throws SQLException {
        Order order;
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = createPreparedStatementforGetOrder(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no order with id: " + id + " in database.");
                return null;
            } else {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                order = new Order(customerId);
                order.setOrderId(orderId);
                System.out.printf("| Order ID: %-3d | Customer ID: %-3d |" + "\n", orderId, customerId);
                return order;
            }
        }
    }

    private static PreparedStatement createPreparedStatementForIsOrderExists(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT order_id FROM orders WHERE order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public static boolean isOrderExists(int orderId) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result;
        try (
                PreparedStatement preparedStatement = createPreparedStatementForIsOrderExists(connection, orderId);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                result = false;
                System.out.println("Order with id \"" + orderId + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        }
    }

    public void updateOrder(int orderId, int customer_id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET customer_id = ? WHERE order_id = ?");
        ) {
            if (isOrderExists(orderId) && CustomersDao.isCustomerExist(customer_id))
            {
                preparedStatement.setInt(1, customer_id);
                preparedStatement.setInt(2, orderId);
                preparedStatement.executeUpdate();
                System.out.println("Order is updated successfully.");
            }
        }
    }

    /**
     *Do czasu aż result set ma kolejne wyniki dodawaj je do (tablicy ??)
     */

//    public int checkOrderItemsByOrderId(int orderId) throws SQLException {
//        Connection connection = JdbcConnector.getConnection();
//        ArrayList <Integer> listOfOrderItemsId = new ArrayList();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("SELECT order_items_id FROM order_items WHERE order_id = ? ")
//        ) {
//            preparedStatement.setInt(1, orderId);
//            if (isOrderExists(orderId)) {
//                ResultSet resultSet = preparedStatement.executeQuery();
//                while(resultSet.next()){
//                    listOfOrderItemsId.add(resultSet.getInt("order_items_id"));
//                }
//                resultSet.close();
//                for()
//                return count;
//            }
//            return count;
//        }
//    }

//    public void deleteOrder(int orderId) throws SQLException {
//        Connection connection = JdbcConnector.getConnection();
//        try (
//                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?");
//        ) {
//            preparedStatement.setInt(1, orderId);
//            preparedStatement.executeUpdate();
//            if (checkOrderItemsByOrderId()) {
//                System.out.println("Product type \"" + typeName + "\" is successfully deleted.");
//            }
//        } catch (MySQLIntegrityConstraintViolationException ex) {
//            int numberOfProducts = getNumberOfProductsOfSpecificType(id);
//            System.out.println("You can't delete product type \"" + typeName + "\", because you have " + numberOfProducts + " products of that type in your database !!");
//        }
//    }
//    };

}
