package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.bootstrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    public Order addNewOrder(Order order) {
        DataHolder.orderList.add(order);
        return order;
    }

    public List<Order> findAll() {
        return DataHolder.orderList;
    }
}
