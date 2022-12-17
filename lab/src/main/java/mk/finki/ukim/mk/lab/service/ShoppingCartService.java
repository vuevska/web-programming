package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<Balloon> listAllProductsInShoppingCart(Long id);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
}
