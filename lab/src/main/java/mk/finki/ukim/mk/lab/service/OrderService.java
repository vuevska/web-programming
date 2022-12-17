package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(String balloonColor, String balloonSize, LocalDateTime dateCreated, Long userId);
    List<Order> findAll();
    List<Order> findAllOrdersByDateBetween(LocalDateTime from, LocalDateTime to);
}
