import java.sql.SQLException;

/**
 * Created by Piotr on 24.02.2018.
 */
public class MainTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        ProductsDao productDao = new ProductsDao();
        //productDao.getProduct(18);
        //productDao.printAllProductsFromProductsTable();
        //productDao.updateProduct(18,"coś", 123.456, 16,12,"Jakiś Dobry");
        //productDao.deleteProduct(18);
        //productDao.createProduct("telewizor", 123.456, 16, 1, "SuperTV");
        //productDao.isProductExists(22);

        ProductTypesDao productTypesDao = new ProductTypesDao();

        //productTypesDao.addNewProductType("TV");
        //productTypesDao.getProductTypeName(3);
        //productTypesDao.deleteProductType(15);
        //productTypesDao.isRecordExists("tv");
        //productTypesDao.updateProductType(16,"TV");
        //System.out.println(productTypesDao.isProductTypeExistsById(4));
        //System.out.println(productTypesDao.getNumberOfProductsOfSpecificType(2));
        productTypesDao.deleteProductType(2);

    }
}
