/**
 * Created by Piotr on 23.02.2018.
 */
public class Product {

    public Product(String productName, float price, int typeId, int quantity, String productBrand) {
        this.productName = productName;
        this.price = price;
        this.typeId = typeId;
        this.quantity = quantity;
        this.productBrand = productBrand;
    }

    private int productId;
    private String productName;
    private float price;
    private int typeId;
    private int quantity;
    private String productBrand;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public float getPrice() {
        return price;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductBrand() {
        return productBrand;
    }
}