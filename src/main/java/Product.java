/**
 * Created by Piotr on 23.02.2018.
 */
public class Product {

    public Product(String productName, double price, int typeId, int quantity, String productBrand) {
        this.productName = productName;
        this.price = price;
        this.typeId = typeId;
        this.quantity = quantity;
        this.productBrand = productBrand;
    }

    private int productId;
    private String productName;
    private double price;
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

    public double getPrice() {
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