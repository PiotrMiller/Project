
import classes.*;
import dao.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DatabaseIntegrationTest {

    @Test
    public void testCrudProduct() {

        CrudDao<Product> crudDao = new ProductsDao();

        // data
        Product insertedProduct = new Product("a", "a", 1f, 22, 1);
        Product productForUpdate = new Product("b", "b", 2f, 22, 2);

        // insert and find ID
        int productId = crudDao.create(insertedProduct);

        assertEquals(productId, insertedProduct.getProductId());

        // read and compare with data (using assertions)
        Product product = crudDao.get(productId);

        assertEquals(product.getProductId(), insertedProduct.getProductId());
        assertEquals(product.getProductBrand(), insertedProduct.getProductBrand());
        assertEquals(product.getProductName(), insertedProduct.getProductName());
        assertEquals(product.getPrice(), insertedProduct.getPrice(), 0.0);
        assertEquals(product.getTypeId(), insertedProduct.getTypeId());
        assertEquals(product.getQuantity(), insertedProduct.getQuantity());

        // update
        crudDao.update(productId, productForUpdate);

        // read and check if updated successfully
        assertEquals(crudDao.get(productId).getProductName(), productForUpdate.getProductName());
        assertEquals(crudDao.get(productId).getProductBrand(), productForUpdate.getProductBrand());
        assertEquals(crudDao.get(productId).getProductName(), productForUpdate.getProductName());
        assertEquals(crudDao.get(productId).getPrice(), productForUpdate.getPrice(), 0.0);
        assertEquals(crudDao.get(productId).getTypeId(), productForUpdate.getTypeId());

        // delete
        crudDao.delete(productId);

        // read and find if deleted
        assertEquals(null, crudDao.get(productId));

//        List<Product> products = crudDao.getAll();
//        for(Product p : products){
//            System.out.println(p.getProductId() + " " + p.getProductBrand() + p.getProductName());
//        }
    }

    @Test
    public void testCrudProductType() {

        CrudDao<ProductType> crudDao = new ProductTypesDao();

        // data
        ProductType insertedProductType = new ProductType("a");
        ProductType productTypeForUpdate = new ProductType("b");

        // insert and find ID
        int productTypeId = crudDao.create(insertedProductType);

        assertEquals(productTypeId, insertedProductType.getProductTypeId());

        // read and compare with data (using assertions)
        ProductType productType = crudDao.get(productTypeId);

        assertEquals(productType.getProductTypeId(), insertedProductType.getProductTypeId());
        assertEquals(productType.getProductTypeName(), insertedProductType.getProductTypeName());

        // update
        crudDao.update(productTypeId, productTypeForUpdate);

        // read and check if updated successfully
        assertEquals(crudDao.get(productTypeId).getProductTypeName(), productTypeForUpdate.getProductTypeName());

        // delete
        crudDao.delete(productTypeId);

        // read and find if deleted
        assertEquals(null, crudDao.get(productTypeId));

//        List<ProductType> productTypes = crudDao.getAll();
//        for(ProductType pt : productTypes){
//            System.out.println(pt.getProductTypeId() + " " + pt.getProductTypeName());
//        }
    }


    @Test
    public void testCrudCustomer() {

        CrudDao<Customer> crudDao = new CustomersDao();

        // data
        Customer insertedCustomer = new Customer("a", "a", "a", "a", 1, "a", 1);
        Customer customerForUpdate = new Customer("b", "b", "b", "b", 2, "b", 2);

        // insert and find ID
        int customerId = crudDao.create(insertedCustomer);

        assertEquals(customerId, insertedCustomer.getCustomerId());

        // read and compare with data (using assertions)
        Customer customer = crudDao.get(customerId);

        assertEquals(customer.getCustomerId(), insertedCustomer.getCustomerId());
        assertEquals(customer.getFirstName(), insertedCustomer.getFirstName());
        assertEquals(customer.getLastName(), insertedCustomer.getLastName());
        assertEquals(customer.getAddress(), insertedCustomer.getAddress());
        assertEquals(customer.getCity(), insertedCustomer.getCity());
        assertEquals(customer.getPostCode(), insertedCustomer.getPostCode());
        assertEquals(customer.getEmail(), insertedCustomer.getEmail());
        assertEquals(customer.getTelephone(), insertedCustomer.getTelephone());

        // update
        crudDao.update(customerId, customerForUpdate);

        // read and check if updated successfully
        assertEquals(crudDao.get(customerId).getFirstName(), customerForUpdate.getFirstName());
        assertEquals(crudDao.get(customerId).getLastName(), customerForUpdate.getLastName());
        assertEquals(crudDao.get(customerId).getAddress(), customerForUpdate.getAddress());
        assertEquals(crudDao.get(customerId).getCity(), customerForUpdate.getCity());
        assertEquals(crudDao.get(customerId).getPostCode(), customerForUpdate.getPostCode());
        assertEquals(crudDao.get(customerId).getEmail(), customerForUpdate.getEmail());
        assertEquals(crudDao.get(customerId).getTelephone(), customerForUpdate.getTelephone());

        // delete
        crudDao.delete(customerId);

        // read and find if deleted
        assertEquals(null, crudDao.get(customerId));

//        List<Customer> customers = crudDao.getAll();
//        for(Customer c : customers){
//            System.out.println(c.getCustomerId() + " " + c.getFirstName() + " " + c.getLastName());
//        }

    }

    @Test
    public void testCrudOrder() {

        CrudDao<Order> crudDao = new OrdersDao();

        // data
        Order insertOrder = new Order(6);
        Order orderForUpdate = new Order(7);

        // insert and find ID
        int orderId = crudDao.create(insertOrder);

        assertEquals(orderId, insertOrder.getOrderId());

        // read and compare with data (using assertions)
        Order order = crudDao.get(orderId);

        assertEquals(order.getCustomerId(), insertOrder.getCustomerId());
        assertEquals(order.getOrderId(), insertOrder.getOrderId());

        // update
        crudDao.update(orderId, orderForUpdate);

        // read and check if updated successfully
        assertEquals(crudDao.get(orderId).getCustomerId(), orderForUpdate.getCustomerId());

        // delete
        crudDao.delete(orderId);

        // read and find if deleted
        assertEquals(null, crudDao.get(orderId));

//        List<Order> ordersList = crudDao.getAll();
//        for(Order o : ordersList){
//            System.out.println(o.getOrderId() + " " + o.getCustomerId());
//        }
    }

    @Test
    public void testCrudOrderItem() {

        CrudDao<OrderItem> crudDao = new OrderItemsDao();

        // data
        OrderItem insertOrderItem = new OrderItem(5, 39, 1);
        OrderItem orderItemForUpdate = new OrderItem(4, 25, 1);

        // insert and find ID
        int orderItemId = crudDao.create(insertOrderItem);

        assertEquals(orderItemId, insertOrderItem.getOrderItemId());


        // read and compare with data (using assertions)
        OrderItem orderItem = crudDao.get(orderItemId);

        assertEquals(orderItem.getOrderItemId(), insertOrderItem.getOrderItemId());
        assertEquals(orderItem.getOrderId(), insertOrderItem.getOrderId());
        assertEquals(orderItem.getProductId(), insertOrderItem.getProductId());
        assertEquals(orderItem.getQuantity(), insertOrderItem.getQuantity());

        // update

        crudDao.update(orderItemId, orderItemForUpdate);


        // read and check if updated successfully

        assertEquals(crudDao.get(orderItemId).getProductId(), orderItemForUpdate.getProductId());
        assertEquals(crudDao.get(orderItemId).getOrderId(), orderItemForUpdate.getOrderId());
        assertEquals(crudDao.get(orderItemId).getQuantity(), orderItemForUpdate.getQuantity());

        // delete
        crudDao.delete(orderItemId);

        // read and find if deleted

        assertEquals(null, crudDao.get(orderItemId));

//        List<OrderItem> orderItemsList = crudDao.getAll();
//        for(OrderItem oi : orderItemsList){
//            System.out.println(oi.getOrderItemId() + " " + oi.getOrderId() + " " + oi.getProductId() + " " + oi.getQuantity());
//        }

    }
}
