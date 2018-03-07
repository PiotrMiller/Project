import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Piotr on 07.03.2018.
 */
public class CustomersDao implements CrudDao<Customer> {

    @Override
    public void create(Customer customer) {

    }

    @Override
    public Customer get(int id) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public Customer createNewCustomer(String firstName, String lastName, String address, String city, int postCode, String email, int telephone) throws SQLException {
        Customer customer = new Customer(firstName, lastName, address, city, postCode, email, telephone);
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (first_name, last_name, address, city, post_code, email, telephone) VALUES (?,?,?,?,?,?,?)");
        ) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setInt(5, postCode);
            preparedStatement.setString(6, email);
            preparedStatement.setInt(7, telephone);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int autoIncrementCustomerId = resultSet.getInt(1);
            customer.setCustomerId(autoIncrementCustomerId);
            System.out.println("New customer is successfully added to the database");
            resultSet.close();
            return customer;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("Cannot add new Customer.");
            System.out.println("Customer with email: " + email + " already exist in database !");
            return null;
        }
    }

    private PreparedStatement createPreparedStatementforGetCustomer (Connection connection, int id)throws SQLException{
        String sqlQuery = "SELECT customer_id, first_name, last_name, address, city, post_code, email, telephone From customers WHERE customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public Customer getCustomer(int id) throws SQLException {
        Customer customer;
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = createPreparedStatementforGetCustomer(connection, id);
                ResultSet result = preparedStatement.executeQuery()
        ) {
            if (!result.next()) {
                System.out.println("There is no customer with id: " + id + " in database.");
                return null;
            } else {
                int customerId = result.getInt("customer_id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String address = result.getString("address");
                String city = result.getString("city");
                int postCode = result.getInt("post_code");
                String email = result.getString("email");
                int telephone = result.getInt("telephone");
                customer = new Customer(firstName, lastName, address, city, postCode, email, telephone);
                customer.setCustomerId(customerId);
                //System.out.printf("| ID: %-3d | First Name: %-15s | Last Name: %-15s | City: %-15s | Post Code: %-7d | Address: %-30s | Email: %-30s | Telephone: %-13d |" + "\n", customerId, firstName, lastName, city, postCode, address, email, telephone);
                return customer;
            }
        }
    }

    private static PreparedStatement createPreparedStatementForIsCustomerExist(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT customer_id FROM customers WHERE customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public static boolean isCustomerExist(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result;
        try (
                PreparedStatement preparedStatement = createPreparedStatementForIsCustomerExist(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                result = false;
                System.out.println("Customer with id \"" + id + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        }
    }

    public void updateCustomer(int customerId, String firstName, String lastName, String address, String city, int postCode, String email, int telephone) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customers SET first_name = ?, last_name = ?, address = ?, city = ?, post_code = ?, email = ?, telephone = ? WHERE customer_id = ?")
        ) {
            if(isCustomerExist(customerId)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, city);
                preparedStatement.setInt(5, postCode);
                preparedStatement.setString(6, email);
                preparedStatement.setInt(7, telephone);
                preparedStatement.setInt(8, customerId);
                preparedStatement.executeUpdate();
                System.out.println("Customer is updated successfully.");
            }
        }
    }

    public void deleteCustomer(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customers WHERE customer_id = ?")
        ) {
            if (isCustomerExist(id)) {
                Customer customer = getCustomer(id);
                String firstName = customer.getFirstName();
                String lastName = customer.getLastName();
                String email = customer.getEmail();
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Customer: " + firstName + " " + lastName + " with email: " + email + " is successfully deleted from database.");
            }
        }
    }
}
