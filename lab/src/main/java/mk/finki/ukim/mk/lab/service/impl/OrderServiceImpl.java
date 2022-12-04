package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.exceptions.CanNotPlaceOrderException;
import mk.finki.ukim.mk.lab.repository.OrderRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(String balloonColor, String balloonSize, String clientName, String address) {
        if (clientName == null || address == null || clientName.isEmpty() || address.isEmpty()) {
            throw new CanNotPlaceOrderException();
        }
        Order order = new Order(balloonColor, balloonSize, clientName, address, 0L);
        return orderRepository.addNewOrder(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}