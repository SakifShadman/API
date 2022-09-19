package Section14.Pojo.CreateOrder;

import Section14.Pojo.CreateOrder.OrderDetail;

import java.util.List;

public class Orders {

    private List<OrderDetail> orders;

    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }
}