package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;

import java.time.LocalDateTime;
import java.util.List;

public interface ShoppingCartService {

    List<Order> listAllOrdersInShoppingCart(Long id);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addOrderToShoppingCart(String username, Long id);
}
