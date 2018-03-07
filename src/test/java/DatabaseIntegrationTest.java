import org.junit.Test;

public class DatabaseIntegrationTest {

    @Test
    public void testCrudProduct() {

        CrudDao<Product> crudDao = new ProductsDao();

        // data
        Product insertedProduct = new Product("", 1f, 0, 1, "");

        // insert and find ID
        int id = crudDao.create(insertedProduct);

        // read and compare with data (using assertions)
        Product product = crudDao.get(id);

        // update

        // read and check if updated successfully

        // delete

        // read and find if deleted
    }

    @Test
    public void testCrudProductType() {

    }

    @Test
    public void testCrudDao1() {

    }

    @Test
    public void testCrudPDao2() {

    }


}
