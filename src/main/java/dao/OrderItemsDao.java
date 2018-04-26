package dao;

import classes.OrderItem;
import db.JdbcConnector;

import java.sql.*;

/**
 * Created by Piotr on 07.03.2018.
 */
public class OrderItemsDao implements CrudDao <OrderItem> {

    /**
     * 1) Jak zabezpieczyć przed stworzeniem zamówienia produktu którego nie ma, lub z order_id które nie istnieja.
     * Jak zabezpieczyć przed złożeniem zamówienia na większą liczbę produktów niż mamy na stanie. stworzyć osobną metodę z zapytaniem do tabeli product ?
     *
     * 2) Jak napisać assercje do create gdzie będzie sprawdzała sytuacje z poprawnymi parametrami i takimi które nie istnieją.
     *
     * 3) Get wyrzuca wyjątek SQLException, nie wiem czemu. Przez co Update też nie działa.
     *
     * 4) W OrderDao w metodzie create sprawdzam czy Customer istnieje w bazie danych za pomocą statycznej metody z klasy Customer.
     * Powinienem to zamienić na dodatkową metodę z klasy OrderDao czy po prostu łapać wyjątek ?
     *
     * 5) Czy metoda delete z OrdersDao może posiadać dwa preperedStatement tak jak teraz, czy powinna wykorzystywać dodatkową metodę ?
     *
     * 6) W ProductsDao w delete usunąłem sprawdzanie, przy przekazaniu poprawnych wartości w databaseIntegrationTest przechodzi.
     *    Przy wywołaniu metody na id którego nie ma w bazie danych wyskakuje NullPointerException bo jest użyta metoda get która zwraca null i chce na niej wywołać jeszcze getter.
     *    Obsługiwać to czy właśnie takie błedy mają wyskakiwać ?
     *
     * 7) Jak napisać test sprawdzający klasę OrderItemsDao jeżeli tabela z bazy danych łączy się i z orders i z products,
     *    powinienem w tych testach tworzyć i dodawać do bazy danych obiekty tych dwóch klas ?
     */

    @Override
    public int create(OrderItem orderItem) {
        Connection connection = JdbcConnector.getConnection();
        int autoIncrementOrderItemsId = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO order_items (order_id, product_id, quantity) values (?,?,?)")) {
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getProductId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            autoIncrementOrderItemsId = resultSet.getInt(1);
            orderItem.setOrderItemId(autoIncrementOrderItemsId);
            System.out.println("New order item is successfully added to the database. OrderItem have id: " + autoIncrementOrderItemsId);
            resultSet.close();

//        } catch (SQLIntegrityConstraintViolationException ex) {
//            System.out.println("Produkt nie istnieje lub nie ma zamówienia o podanym id.");
//            throw new SQLIntegrityConstraintViolationException();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return autoIncrementOrderItemsId;
    }

    @Override
    public OrderItem get(int id) {
        Connection connection = JdbcConnector.getConnection();
        OrderItem orderItem = null;
        try (
                PreparedStatement preparedStatement = createPSforGet(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            int orderItemsId = resultSet.getInt("order_items_id");
            int orderId = resultSet.getInt("order_id");
            int productId = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");
            orderItem = new OrderItem(orderId, productId, quantity);
            orderItem.setOrderItemId(orderItemsId);
            System.out.printf("| Order Items ID: %-3d | Order ID: %-3d | ProductId ID: %-3d | Quantity: %-3d |" + "\n", orderItemsId, orderId, productId, quantity);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orderItem;
    }

    @Override
    public OrderItem update(int id, OrderItem orderItem) {
        Connection connection = JdbcConnector.getConnection();
        OrderItem updatedOrderItem = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET order_id = ?, product_id = ?, quantity = ?  WHERE order_items_id = ?")
        ) {
            if (get(id) != null) {
                preparedStatement.setInt(1, orderItem.getOrderId());
                preparedStatement.setInt(2, orderItem.getProductId());
                preparedStatement.setInt(3, orderItem.getQuantity());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                updatedOrderItem = get(id);
                System.out.println("Order Item with id: " + id + " is updated successfully.");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("There is no Order Item with id: " + orderItem.getOrderItemId() + " in database");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updatedOrderItem;
    }

    @Override
    public void delete(int id) {
        Connection connection = db.JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Order Item with id: " + id + " is successfully deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private PreparedStatement createPSforGet(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT order_items_id, order_id, product_id, quantity  From order_items WHERE order_items_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
