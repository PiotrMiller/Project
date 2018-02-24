import java.sql.SQLException;

/**
 * Created by Piotr on 24.02.2018.
 */
public class MainTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        ProductDao productDao = new ProductDao("MySQLPassword");
        productDao.getProduct(2);
        productDao.printAllProductsFromProductsTable();

    }
}
