import java.sql.SQLException;


/**
 * Created by Piotr on 24.02.2018.
 */
public class MainTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {

        ProductsDao productDao = new ProductsDao();
        //productDao.createProduct("LG", "49\" - LED - 1080p - HDTV", 299.99f, 21, 2);   //+
        //productDao.deleteProduct(19); //+
        //productDao.getProduct(24);    //+
        //productDao.printAllProductsFromProductsTable();   //+
        //productDao.updateProduct(111,"coś", "", 123.456f, 21,12);  //+
        //System.out.println(productDao.isProductExists(24));   //+

        ProductTypesDao productTypesDao = new ProductTypesDao();

        //productTypesDao.addNewProductType("TV");           //+
        //productTypesDao.getProductTypeName(20);            //+
        //productTypesDao.deleteProductType(25);             //+
        //productTypesDao.updateProductType(21,"TV");        //+
        //System.out.println(productTypesDao.isProductTypeExistsById(24));  //+
        //System.out.println(productTypesDao.getNumberOfProductsOfSpecificType(19));    //+
        //System.out.println(productTypesDao.isProductTypeExistsByName("ttv")); //+

        CustomersDao customersDao = new CustomersDao();

        //customersDao.createNewCustomer("Marek", "Wiśniewski", "Asfaltowa 2/3", "Sopot",43567, "marekwisniewski@gmail.com" , 123987456);
        //customersDao.getCustomer(1);
        //customersDao.updateCustomer(8, "Marek", "Wiśniewski", "Asfaltowa 2/3", "Sopot", 43567, "marekwisniewski@gmail.com", 111111111);
        //customersDao.deleteCustomer(8);

        OrdersDao ordersDao = new OrdersDao();

        //ordersDao.createNewOrder(1);
        //ordersDao.getOrder(1);
        //ordersDao.isOrderExists(3);
        //ordersDao.updateOrder(3, 6);
        System.out.println(ordersDao.checkOrderItemsByOrderId(1));

        OrderItemsDao orderItemsDao = new OrderItemsDao();

        orderItemsDao.createNewOrderItem(3, 18, 1);

        System.out.println(ordersDao.checkOrderItemsByOrderId(3));
    }
}
