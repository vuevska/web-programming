package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.BalloonAlreadyInShoppingCartException;
import mk.finki.ukim.mk.lab.model.exceptions.BalloonNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.UserNotFoundException;
import mk.finki.ukim.mk.lab.model.exceptions.UserWithIdNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.BalloonRepository;
import mk.finki.ukim.mk.lab.repository.jpa.OrderRepository;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BalloonRepository balloonRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, BalloonRepository balloonRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.balloonRepository = balloonRepository;
    }

    @Override
    @Transactional
    public Order placeOrder(String balloonColor, String balloonSize, LocalDateTime dateCreated, Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserWithIdNotFoundException(userId));
        return orderRepository.save(new Order(balloonColor, balloonSize, dateCreated, user));
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllOrdersByDateBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findByDateCreatedBetween(from, to);
    }

}