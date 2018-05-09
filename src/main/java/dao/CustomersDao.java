package dao;

import classes.Customer;
import db.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 07.03.2018.
 */
public class CustomersDao implements CrudDao <Customer> {

    @Override
    public int create(Customer customer) {
        Connection connection = JdbcConnector.getConnection();
        int autoIncrementCustomerId = 0;

        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (first_name, last_name, address, city, post_code, email, telephone) VALUES (?,?,?,?,?,?,?)")
        ) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getCity());
            preparedStatement.setInt(5, customer.getPostCode());
            preparedStatement.setString(6, customer.getEmail());
            preparedStatement.setInt(7, customer.getTelephone());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            autoIncrementCustomerId = resultSet.getInt(1);
            customer.setCustomerId(autoIncrementCustomerId);
            System.out.println("New customer: " + customer.getFirstName() + " " + customer.getLastName() + " is successfully added to the database." +
                    " Customer have id: " + autoIncrementCustomerId);
            resultSet.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Cannot add new Customer.");
            System.out.println("Customer with email: " + customer.getEmail() + " already exist in database !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return autoIncrementCustomerId;
    }

    @Override
    public Customer get(int id) {
        Connection connection = JdbcConnector.getConnection();
        Customer customer = null;
        try (
                PreparedStatement preparedStatement = createPSForGet(connection, id);
                ResultSet result = preparedStatement.executeQuery()
        ) {
            if (!result.next()) {
                System.out.println("There is no customer with id: " + id + " in database.");
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
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer update(int id, Customer customer) {
        Connection connection = JdbcConnector.getConnection();
        Customer updatedCustomer = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customers SET first_name = ?, last_name = ?, address = ?, city = ?, post_code = ?, email = ?, telephone = ? WHERE customer_id = ?")
        ) {
            if (get(id) != null) {
                preparedStatement.setString(1, customer.getFirstName());
                preparedStatement.setString(2, customer.getLastName());
                preparedStatement.setString(3, customer.getAddress());
                preparedStatement.setString(4, customer.getCity());
                preparedStatement.setInt(5, customer.getPostCode());
                preparedStatement.setString(6, customer.getEmail());
                preparedStatement.setInt(7, customer.getTelephone());
                preparedStatement.setInt(8, id);
                preparedStatement.executeUpdate();
                updatedCustomer = get(id);
                System.out.println("Customer with id: " + id + " is updated successfully.");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Cannot update customer with email: " + customer.getEmail() + ". This email already exist in database !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updatedCustomer;
    }

    @Override
    public void delete(int id) {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customers WHERE customer_id = ?")
        ) {
            if (get(id) != null) {
                Customer customer = get(id);
                String firstName = customer.getFirstName();
                String lastName = customer.getLastName();
                String email = customer.getEmail();
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Customer: " + firstName + " " + lastName + " with email: " + email + ", is successfully deleted from database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAll() {
            List<Customer> CustomerList = new ArrayList<>();
            Connection connection = JdbcConnector.getConnection();

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT customer_id, first_name, last_name, address, city, post_code, email, telephone FROM customers");
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    int postCode = resultSet.getInt("post_code");
                    String email = resultSet.getString("email");
                    int telephone = resultSet.getInt("telephone");

                    Customer customer = new Customer(firstName,lastName,address,city,postCode,email,telephone);
                    customer.setCustomerId(customerId);
                    CustomerList.add(customer);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return CustomerList;
        }

    private PreparedStatement createPSForGet(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT customer_id, first_name, last_name, address, city, post_code, email, telephone From customers WHERE customer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }
}