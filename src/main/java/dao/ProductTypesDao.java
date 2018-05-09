package dao;

import classes.ProductType;
import db.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 27.02.2018.
 */
public class ProductTypesDao implements CrudDao<ProductType> {

    @Override
    public int create(ProductType productType) {
        Connection connection = JdbcConnector.getConnection();
        String productTypeName = productType.getProductTypeName();
        int autoIncrementProductTypeId = 0;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product_types (type_name) VALUES (?)")
        ) {
            preparedStatement.setString(1, productTypeName);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            autoIncrementProductTypeId = resultSet.getInt(1);
            productType.setProductTypeId(autoIncrementProductTypeId);
            System.out.println("New product type \"" + productTypeName + "\" is successfully added to the database. classes.Product type have id: " + autoIncrementProductTypeId);
            resultSet.close();
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("\"" + productTypeName + "\" product type already exist in database\"");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return autoIncrementProductTypeId;
    }

    @Override
    public ProductType get(int id) {
        Connection connection = JdbcConnector.getConnection();
        ProductType productType = null;
        try (
                PreparedStatement preparedStatement = createPSForGetProductType(connection, id);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (!resultSet.next()) {
                System.out.println("There is no product type with id: " + id + " in database.");
            } else {
                int productTypeId = resultSet.getInt("type_id");
                String productTypeName = resultSet.getString("type_name");
                productType = new ProductType(productTypeName);
                productType.setProductTypeId(productTypeId);
                //System.out.printf("| ID: %-3d | classes.Product Type Name: %-10s |" + "\n", productTypeId, productTypeName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productType;
    }

    @Override
    public ProductType update(int id, ProductType productType) {
        Connection connection = JdbcConnector.getConnection();
        ProductType updatedProductType = null;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product_types SET type_name = ? WHERE type_id = ?")
        ) {
            if (get(id) != null) {
                preparedStatement.setString(1, productType.getProductTypeName());
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                updatedProductType = get(id);
                System.out.println("Product type with ID \"" + id + "\" updeted successfully.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updatedProductType;
    }

    @Override
    public void delete(int id) {
        Connection connection = JdbcConnector.getConnection();
        if (get(id) != null) {
            String productTypeName = get(id).getProductTypeName();
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product_types WHERE type_id = ?")
            ) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Product type \"" + productTypeName + "\" is successfully deleted.");
            } catch (SQLIntegrityConstraintViolationException ex) {
                int numberOfProducts = getNumberOfProductsOfSpecificType(id);
                System.out.println("You can't delete product type \"" + productTypeName + "\", because you have " + numberOfProducts + " products of that type in your database !!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<ProductType> getAll() {
        List<ProductType> productTypesList = new ArrayList<>();
        Connection connection = JdbcConnector.getConnection();

        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_id, type_name FROM product_types");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                int typeId = resultSet.getInt("type_id");
                String typeName = resultSet.getString("type_name");

                ProductType productType = new ProductType(typeName);
                productType.setProductTypeId(typeId);
                productTypesList.add(productType);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productTypesList;
    }

    private PreparedStatement createPSForGetProductType(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT type_id, type_name FROM product_types WHERE type_id = ?");
        preparedStatement.setInt(1, id);
        return preparedStatement;
    }

    private int getNumberOfProductsOfSpecificType(int id) {
        Connection connection = JdbcConnector.getConnection();
        int count = 0;
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM products WHERE type_id = ? ")
        ) {
            preparedStatement.setInt(1, id);
            if (get(id) != null) {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                count = resultSet.getInt(1);
                resultSet.close();
                return count;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }
}