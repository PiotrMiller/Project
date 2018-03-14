package classes;

/**
 * Created by Piotr on 06.03.2018.
 */
public class Order {

    public Order(int customerId){
        this.customerId = customerId;
    }

    private int orderId;
    private int customerId;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
