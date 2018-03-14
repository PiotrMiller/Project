package classes;

/**
 * Created by Piotr on 23.02.2018.
 */
public class Product {

    public Product(String productBrand, String productName, float price, int typeId, int quantity) {
        this.productBrand = productBrand;
        this.productName = productName;
        this.price = price;
        this.typeId = typeId;
        this.quantity = quantity;
    }

    private String productBrand;
    private int productId;
    private String productName;
    private float price;
    private int typeId;
    private int quantity;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductBrand() {
        return productBrand;
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
}