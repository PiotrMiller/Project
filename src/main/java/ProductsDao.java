import java.sql.*;

/**
 * Created by Piotr on 23.02.2018.
 */
public class ProductsDao {

    public Product createProduct(String productName, double price, int typeId, int quantity, String productBrand) throws SQLException {
        Product product = new Product(productName, price, typeId, quantity, productBrand);
        Connection connection = JdbcConnector.getCconnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO products (ProductName, Price, TypeID, Quantity, ProductBrand) values (?,?,?,?,?);");
        try {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, typeId);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, productBrand);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int autoIncrementProductId = resultSet.getInt(1);
            product.setProductId(autoIncrementProductId);
            System.out.println("New product is successfully added to the database");
        } finally {
            preparedStatement.close();
        }
        return product;
    }

    public void deleteProduct(int id) throws SQLException {
        Connection connection = JdbcConnector.getCconnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE ProductID = ?");
        try {
            if (isProductExists(id)) {
                String productName = getProduct(id).getProductName();
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                System.out.println("Product \"" + productName + "\" is successfully deleted from database.");
            }
        } finally {
            preparedStatement.close();
        }
    }

    public Product getProduct(int id) throws SQLException {
        Product product;
        Connection connection = JdbcConnector.getCconnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductID, ProductName, Price, TypeID, Quantity, ProductBrand From products WHERE ProductID = (?)");
        preparedStatement.setInt(1, id);
        ResultSet result = preparedStatement.executeQuery();
        try {
            if (!result.next()) {
                throw new SQLException("There is no product with id: " + id + " in database.");
            } else {
                int productID = result.getInt("ProductID");
                String name = result.getString("ProductName");
                double price = result.getDouble("Price");
                int typeID = result.getInt("TypeID");
                int quantity = result.getInt("Quantity");
                String brand = result.getString("ProductBrand");
                product = new Product(name, price, typeID, quantity, brand);
                product.setProductId(productID);
                //System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-50s | Price: %.2f |" + "\n", id, brand, name, price);
                preparedStatement.close();
                return product;
            }
        } finally {
            preparedStatement.close();
        }
    }

    public boolean isProductExists(int id) throws SQLException {
        Connection connection = JdbcConnector.getCconnection();
        boolean result = false;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductID FROM products WHERE ProductID = \"" + id + "\"");
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (!resultSet.next()) {
                result = false;
                System.out.println("Product with id \"" + id + "\" doesn't exist in database");
            } else {
                result = true;
            }
            return result;
        } finally {
            preparedStatement.close();
        }
    }


    public void updateProduct(int productId, String name, double price, int typeId, int quantity, String brand) throws SQLException {
        Connection connection = JdbcConnector.getCconnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET ProductName = ?, Price = ?, TypeID = ?, Quantity = ?, ProductBrand= ? WHERE ProductID = ?");
        try {
            if (isProductExists(productId)) {
                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, typeId);
                preparedStatement.setInt(4, quantity);
                preparedStatement.setString(5, brand);
                preparedStatement.setInt(6, productId);
                preparedStatement.executeUpdate();
                System.out.println("Product is updated successfully.");
            }
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
            System.out.println("There is no product type ID like: " + typeId + " in database.\n" +
                    "please create new product type in database or check type Id which you want to update. ");
        } finally {
            preparedStatement.close();
        }
    }

    public void printAllProductsFromProductsTable() throws SQLException {

        Connection connection = JdbcConnector.getCconnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select ProductID, ProductBrand, ProductName, Price, TypeID, Quantity From products");
        ResultSet result = preparedStatement.executeQuery();
        try {
            while (result.next()) {
                int id = result.getInt("ProductID");
                String brand = result.getString("ProductBrand");
                String name = result.getString("ProductName");
                double price = result.getDouble("Price");
                int typeID = result.getInt("TypeID");
                int quantity = result.getInt("Quantity");
                System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-45s | Price: %.2f | Quantity: %-3d | TypeID: %-3d" + "\n", id, brand, name, price, quantity, typeID);
            }
        } finally {
            result.close();
            preparedStatement.close();
        }
    }
}