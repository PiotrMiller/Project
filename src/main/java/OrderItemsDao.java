import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Piotr on 07.03.2018.
 */
public class OrderItemsDao {

    /**
     * Sprawdzić czy jest wystarczająca ilość towaru.
     * Czy && czy dwa if'y ? chyba lepiej ify lepiej można obsłużyć.
     */
    public OrderItem createNewOrderItem(int orderId, int productId, int quantity) throws SQLException {
        OrderItem orderitem = new OrderItem(orderId, productId, quantity);
        Connection connection = JdbcConnector.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity) values (?,?,?)"))
        {
            if (ProductsDao.isProductExists(productId) && OrdersDao.isOrderExists(orderId)) {
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, productId);
                preparedStatement.setInt(3, quantity);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                int autoIncrementOrderItemId = resultSet.getInt(1);
                orderitem.setOrderItemId(autoIncrementOrderItemId);
                System.out.println("New order item is successfully added to the database");
                resultSet.close();
                return orderitem;
            }
            return null;
        }
    }

    public void getOrderItem(){};

    public void updateOrderItem(){};

    public void deleteOrderItem(){};

}
