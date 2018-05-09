package dao;

import classes.Order;
import db.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 07.03.2018.
 */
public class OrdersDao implements CrudDao <Order> {

    @Override
    public int create(Order order) {
        Connection connection = JdbcConnector.getConnection();
        int autoIncrementOrderId = 0;
        int customerId = order.getCustomerId();

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO orders (customer_id) values (?)")) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            autoIncrementOrderId = resultSet.getInt(1);
            order.setOrderId(autoIncrementOrderId);
            System.out.println("New order is successfully added to the database by customer with id: " + customerId + ". Order id: " + autoIncrementOrderId);
            resultSet.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Can't find Customer for this order.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return autoIncrementOrderId;
    }

    @Override
    public Order get(int id) {
        Connection connection = JdbcConnector.getConnection();
        Order order = null;

        try (
                PreparedStatement preparedStatement = createPSforGet(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no order with id: " + id + " in database.");
            } else {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                order = new Order(customerId);
                order.setOrderId(orderId);
                //System.out.printf("| Order ID: %-3d | Customer ID: %-3d |" + "\n", orderId, customerId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    @Override
    public Order update(int id, Order order) {
        Connection connection = JdbcConnector.getConnection();
        Order updatedOrder = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders SET customer_id = ? WHERE order_id = ?")
        ) {
            if (get(id) != null) {
                preparedStatement.setInt(1, order.getCustomerId());
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                updatedOrder = get(id);
                System.out.println("Order with id: " + id + " is updated successfully.");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("There is no customer with id: " + order.getCustomerId() + " in database");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updatedOrder;
    }

    @Override
    public void delete(int id) {
        Connection connection = db.JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
                PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM orders WHERE order_id = ?")
        ) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            preparedStatement2.setInt(1, id);
            preparedStatement2.executeUpdate();
            System.out.println("Order with id: " + id + " is successfully deleted with all ordered products.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Order> getAll() {
            List<Order> ordersList = new ArrayList<>();
            Connection connection = JdbcConnector.getConnection();

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT order_id, customer_id FROM orders");
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int customerId = resultSet.getInt("customer_id");

                    Order customer = new Order(customerId);
                    customer.setOrderId(orderId);
                    ordersList.add(customer);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return ordersList;
        }

    private PreparedStatement createPSforGet(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT order_id, customer_id From orders WHERE order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}
