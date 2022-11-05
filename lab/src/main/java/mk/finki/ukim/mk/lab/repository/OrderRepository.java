package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    public Order addNewOrder(Order order) {
        DataHolder.orderList.add(order);
        return order;
    }
}
