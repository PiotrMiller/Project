import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

/**
 * Created by Piotr on 23.02.2018.
 */
public class ProductsDao {

    public Product createProduct(String productBrand, String productName, float price, int typeId, int quantity) throws SQLException {
        Product product = new Product(productName, price, typeId, quantity, productBrand);
        Connection connection = JdbcConnector.getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (product_brand, product_name, price, type_id, quantity) values (?,?,?,?,?)")
        ) {
            preparedStatement.setString(1, productBrand);
            preparedStatement.setString(2, productName);
            preparedStatement.setFloat(3, price);
            preparedStatement.setInt(4, typeId);
            preparedStatement.setInt(5, quantity);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int autoIncrementProductId = resultSet.getInt(1);
            product.setProductId(autoIncrementProductId);
            System.out.println("New product is successfully added to the database");
            resultSet.close();
        }catch(MySQLIntegrityConstraintViolationException ex){
            System.out.println("Product adding failed ! There is no product type with type_id = " + typeId);
        }
        return product;
    }

    public void deleteProduct(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE product_id = ?");
        ) {
            if (isProductExists(id)) {
                String productName = getProduct(id).getProductName();
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Product \"" + productName + "\" is successfully deleted from database.");
            }
        }
    }

    private PreparedStatement createPreparedStatementforGetProduct (Connection connection, int id)throws SQLException{
        String sqlQuery = "SELECT product_id, product_brand, product_name, price, type_id, quantity From products WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public Product getProduct(int id) throws SQLException {
        Product product;
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = createPreparedStatementforGetProduct(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no product with id: " + id + " in database.");
                return null;
            } else {
                int productID = resultSet.getInt("product_id");
                String brand = resultSet.getString("product_brand");
                String name = resultSet.getString("product_name");
                float price = resultSet.getFloat("price");
                int typeID = resultSet.getInt("type_id");
                int quantity = resultSet.getInt("quantity");
                product = new Product(name, price, typeID, quantity, brand);
                product.setProductId(productID);
                System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-70s | Price: %-8.2f | Quantity: %-3d | TypeID: %-3d |" + "\n", id, brand, name, price, quantity, typeID);
                return product;
            }
        }
    }

    private static PreparedStatement createPreparedStatementForIsProductExists(Connection connection, int id) throws SQLException {
        String sqlQuery = "SELECT product_id FROM products WHERE product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public static boolean isProductExists(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result;
        try (
                PreparedStatement preparedStatement = createPreparedStatementForIsProductExists(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                result = false;
                System.out.println("Product with id \"" + id + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        }
    }

    public void updateProduct(int productId, String brand, String name, float price, int typeId, int quantity) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET product_brand= ?, product_name = ?, price = ?, type_id = ?, quantity = ? WHERE product_id = ?");
        ) {
            if (isProductExists(productId)) {
                preparedStatement.setString(1, brand);
                preparedStatement.setString(2, name);
                preparedStatement.setFloat(3, price);
                preparedStatement.setInt(4, typeId);
                preparedStatement.setInt(5, quantity);
                preparedStatement.setInt(6, productId);
                preparedStatement.executeUpdate();
                System.out.println("Product is updated successfully.");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            System.out.println("There is no product type ID like: " + typeId + " in database.\n" +
                    "please create new product type in database or check type Id which you want to update. ");
        }
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
                double price = result.getFloat("price");
                int typeID = result.getInt("type_id");
                int quantity = result.getInt("quantity");
                System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-70s | Price: %-8.2f | Quantity: %-3d | TypeID: %-3d" + "\n", id, brand, name, price, quantity, typeID);
            }
        }
    }
}