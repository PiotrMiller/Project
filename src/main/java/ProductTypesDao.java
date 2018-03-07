import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

/**
 * Created by Piotr on 27.02.2018.
 */
public class ProductTypesDao {

    private PreparedStatement createPreparedStatementForIsProductTypeExistsByName(Connection connection, String typeName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_name FROM product_types WHERE type_name = ?");
        preparedStatement.setString(1, typeName);
        return preparedStatement;
    }

    public boolean isProductTypeExistsByName(String typeName) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result = false;
        try (
                PreparedStatement preparedStatement = createPreparedStatementForIsProductTypeExistsByName(connection, typeName);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("Product type \"" + typeName + "\" doesn't exist in database");

            } else {
                System.out.println("\"" + typeName + "\" product type already exist in database");
                result = true;
            }
            return result;
        }
    }

    private PreparedStatement createPreparedStatementForIsProductTypeExistsById(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_id FROM product_types WHERE type_id = ? ");
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public boolean isProductTypeExistsById(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        boolean result = false;
        try (
                PreparedStatement preparedStatement = createPreparedStatementForIsProductTypeExistsById(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("Product type with id: \"" + id + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        }
    }

    public void addNewProductType(String productType) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product_types (type_name) VALUES (?)")
        ) {
            preparedStatement.setString(1, productType);
            if (!isProductTypeExistsByName(productType)) {
                preparedStatement.executeUpdate();
                System.out.println("New product type \"" + productType + "\" is successfully added to the database.");
            }
        }
    }

    private PreparedStatement createPreparedStatementForGetProductTypeName(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_name FROM product_types WHERE type_id = ?");
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    public String getProductTypeName(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        try (
                PreparedStatement preparedStatement = createPreparedStatementForGetProductTypeName(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no product type with id: " + id + " in database.");
                return null;

            } else {
                String productTypeName = resultSet.getString("type_name");
                return productTypeName;
            }
        }
    }

    public int getNumberOfProductsOfSpecificType(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        int count = 0;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products WHERE type_id = ? ");
        ) {
            preparedStatement.setInt(1, id);
            if (isProductTypeExistsById(id)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt(1);
                resultSet.close();
                return count;
            }
            return count;
        }
    }

    public void deleteProductType(int id) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        String typeName = getProductTypeName(id);
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product_types WHERE type_id = ?");
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            if (typeName != null) {
                System.out.println("Product type \"" + typeName + "\" is successfully deleted.");
            }
        } catch (MySQLIntegrityConstraintViolationException ex) {
            int numberOfProducts = getNumberOfProductsOfSpecificType(id);
            System.out.println("You can't delete product type \"" + typeName + "\", because you have " + numberOfProducts + " products of that type in your database !!");
        }
    }

    public void updateProductType(int id, String productTypeName) throws SQLException {
        Connection connection = JdbcConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_name FROM product_types WHERE type_id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (!resultSet.next()) {
                System.out.println("There is no record with id: " + id + " in database.");
            } else {
                String oldProductTypeName = resultSet.getString("type_name");
                preparedStatement = connection.prepareStatement("UPDATE product_types SET type_name = ? WHERE type_id = ?");
                preparedStatement.setString(1, productTypeName);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                System.out.println("Product type name with ID \"" + id + "\" updeted successfully, from \"" + oldProductTypeName + "\" to \"" + productTypeName + "\".");
            }
        } finally {
            resultSet.close();
            preparedStatement.close();
        }
    }
}