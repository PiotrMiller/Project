package dao;

import classes.Product;
import db.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 23.02.2018.
 */

/**
 * Catch SQLException czy throws ?
 */
public class ProductsDao implements CrudDao<Product> {

    @Override
    public int create(Product product) {
        Connection connection = JdbcConnector.getConnection();
        int autoIncrementProductId = 0;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (product_brand, product_name, price, type_id, quantity) values (?,?,?,?,?)")
        ) {
            preparedStatement.setString(1, product.getProductBrand());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setInt(4, product.getTypeId());
            preparedStatement.setInt(5, product.getQuantity());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            autoIncrementProductId = resultSet.getInt(1);
            product.setProductId(autoIncrementProductId);
            System.out.println("New product \"" + product.getProductName() + "\" is successfully added to the database. Product have id: " + autoIncrementProductId);
            resultSet.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("Product adding failed ! There is no product type with type_id = " + product.getTypeId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return autoIncrementProductId;
    }


    @Override
    public Product get(int id) {
        Connection connection = JdbcConnector.getConnection();
        Product product = null;
        try (
                PreparedStatement preparedStatement = createPSforGet(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no product with id: " + id + " in database.");
            } else {
                int productID = resultSet.getInt("product_id");
                String brand = resultSet.getString("product_brand");
                String name = resultSet.getString("product_name");
                float price = resultSet.getFloat("price");
                int typeID = resultSet.getInt("type_id");
                int quantity = resultSet.getInt("quantity");
                product = new Product(brand, name, price, typeID, quantity);
                product.setProductId(productID);
                //System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-50s | Price: %-8.2f | Quantity: %-3d | TypeID: %-3d |" + "\n", id, brand, name, price, quantity, typeID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return product;
    }

    @Override
    public Product update(int id, Product product) {
        Connection connection = JdbcConnector.getConnection();
        Product updatedProduct = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET product_brand= ?, product_name = ?, price = ?, type_id = ?, quantity = ? WHERE product_id = ?")
        ) {
                preparedStatement.setString(1, product.getProductBrand());
                preparedStatement.setString(2, product.getProductName());
                preparedStatement.setFloat(3, product.getPrice());
                preparedStatement.setInt(4, product.getTypeId());
                preparedStatement.setInt(5, product.getQuantity());
                preparedStatement.setInt(6, id);
                preparedStatement.executeUpdate();
                updatedProduct = get(id);
                System.out.println("Product with id: " + id + " is updated successfully.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updatedProduct;
    }

    @Override
    public void delete(int id) {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE product_id = ?")
        ) {
                String productName = get(id).getProductName();
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Product \"" + productName + "\" is successfully deleted from database.");

      //  } catch (NullPointerException ex) {
            System.out.println("The is no product with this id.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> productsList = new ArrayList<>();
        Connection connection = JdbcConnector.getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT product_id, product_brand, product_name, price, type_id, quantity FROM products");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String brand = resultSet.getString("product_brand");
                String name = resultSet.getString("product_name");
                float price = resultSet.getFloat("price");
                int typeID = resultSet.getInt("type_id");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(brand, name, price, typeID, quantity);
                product.setProductId(id);
                productsList.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productsList;
    }

    private PreparedStatement createPSforGet(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT product_id, product_brand, product_name, price, type_id, quantity From products WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    private static PreparedStatement createPSForIsProductExists(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT product_id FROM products WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public void printAllProductsFromProductsTable() throws SQLException {

        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("Select product_id, product_brand, product_name, price, type_id, quantity From products");
                ResultSet result = preparedStatement.executeQuery()
        ) {
            while (result.next()) {
                int id = result.getInt("product_id");
                String brand = result.getString("product_brand");
                String name = result.getString("product_name");
                float price = result.getFloat("price");
                int typeID = result.getInt("type_id");
                int quantity = result.getInt("quantity");
                System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-70s | Price: %-8.2f | Quantity: %-3d | TypeID: %-3d" + "\n", id, brand, name, price, quantity, typeID);
            }
        }
    }
}