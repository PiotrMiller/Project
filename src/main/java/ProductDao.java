import java.sql.*;

/**
 * Created by Piotr on 23.02.2018.
 */
public class ProductDao {

    private Connection connection;

    private final static String DB_URL = "jdbc:mysql://localhost:3306/shopdb";
    private final static String DB_USER_NAME = "root";
    private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_PASSWORD;

    public ProductDao(String dbPassword) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class.forName(DB_DRIVER).newInstance();
        DB_PASSWORD = dbPassword;
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        if (!connection.isClosed()) {
            System.out.println("Connected Successfully !");
        } else {
            throw new RuntimeException("Failed to connect to the database !");
        }
    }

    private void disconnect() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();
            if (connection.isClosed()) {
                System.out.println("Connection is Closed !");
            } else {
                throw new RuntimeException("Failed to disconnect from the database !");
            }
        }
    }

    public void deleteProduct(int id) throws SQLException {
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM products WHERE productID = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } finally {
            disconnect();
        }
    }

    public Product createProduct (String productName, double price, int typeId, int quantity, String productBrand) throws SQLException {
        Product product = new Product(productName, price, typeId,quantity,productBrand);
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("insert INTO products (ProductName, Price, TypeID, Quantity, ProductBrand) values (?,?,?,?,?);");
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
            System.out.println(product.getProductId());
        } finally {
            disconnect();
        }
        return product;
    }

    public Product getProduct(int id) throws SQLException {
        Product product;
        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ProductID, ProductName, Price, TypeID, Quantity, ProductBrand From products WHERE ProductID = (?)");
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            int productID = result.getInt("ProductID");
            String name = result.getString("ProductName");
            double price = result.getDouble("Price");
            int typeID = result.getInt("TypeID");
            int quantity = result.getInt("Quantity");
            String brand = result.getString("ProductBrand");
            product = new Product(name, price, typeID, quantity, brand);
            product.setProductId(productID);
            System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-50s | Price: %.2f |" + "\n", id, brand, name, price);
        } finally {
            disconnect();
        }
        return product;
    }

    /**
    public Product updateProduct (Product product) throws SQLException {

            String name = product.getProductName();
            double price = product.getPrice();
            int typeId = product.getTypeId();
            int quantity = product.getQuantity();
            String brand = product.getProductBrand();
            int productId = product.getProductId();

        try {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE products SET ProductName =(?), Price=(?), TypeID=(?), Quantity=(?), ProductBrand=(?) WHERE ProductID = (?)");
           preparedStatement.setString(1, name);
           preparedStatement.setDouble(2, price);
           preparedStatement.setInt(3,typeId);
           preparedStatement.setInt(4,quantity);
           preparedStatement.setString(5,brand);
           preparedStatement.setInt(6, productId);
            preparedStatement.executeUpdate();
        } finally {
            disconnect();
        }
        return new Product(name, price, typeId, quantity, brand);
    }
    */

    public void addNewProductType(String productType) throws SQLException {
        connect();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product_types (TypeName) VALUES (?)");
        preparedStatement.setString(1,productType);
        preparedStatement.executeUpdate();
        disconnect();
    }

    public void printAllProductsFromProductsTable() throws SQLException {

        connect();
        PreparedStatement preparedStatement = connection.prepareStatement("Select ProductID, ProductBrand, ProductName, Price, TypeID, Quantity From products");
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()) {
            int id = result.getInt("ProductID");
            String brand = result.getString("ProductBrand");
            String name = result.getString("ProductName");
            double price = result.getDouble("Price");
            int typeID = result.getInt("TypeID");
            int quantity = result.getInt("Quantity");
            System.out.printf("| ID: %-3d | Brand: %-10s | Name: %-45s | Price: %.2f | Quantity: %-3d | TypeID: %-3d" + "\n", id, brand, name, price, quantity, typeID);
        }
        result.close();
        disconnect();
    }
}
