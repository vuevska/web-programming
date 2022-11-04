package mk.ukim.finki.webprogrammingaud.service.impl;

import mk.ukim.finki.webprogrammingaud.exceptions.ProductAlreadyInShoppingCartException;
import mk.ukim.finki.webprogrammingaud.exceptions.ProductNotFoundException;
import mk.ukim.finki.webprogrammingaud.exceptions.ShoppingCartNotFound;
import mk.ukim.finki.webprogrammingaud.exceptions.UserNotFoundException;
import mk.ukim.finki.webprogrammingaud.model.Product;
import mk.ukim.finki.webprogrammingaud.model.ShoppingCart;
import mk.ukim.finki.webprogrammingaud.model.User;
import mk.ukim.finki.webprogrammingaud.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.webprogrammingaud.repository.InMemoryShoppingCartRepository;
import mk.ukim.finki.webprogrammingaud.repository.InMemoryUserRepository;
import mk.ukim.finki.webprogrammingaud.service.ProductService;
import mk.ukim.finki.webprogrammingaud.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final InMemoryShoppingCartRepository shoppingCartRepository;
    private final InMemoryUserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartServiceImpl(InMemoryShoppingCartRepository shoppingCartRepository, InMemoryUserRepository userRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if (this.shoppingCartRepository.findById(cartId).isEmpty()) {
            throw new ShoppingCartNotFound(cartId);
        }
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return shoppingCartRepository.findByUsernameAndStatus(username, ShoppingCartStatus.CREATED).orElseGet(() -> {
            User user = this.userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException(username));
            ShoppingCart shoppingCart = new ShoppingCart(user);
            return this.shoppingCartRepository.save(shoppingCart);
        });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productService
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getProducts()
                .stream()
                .filter(p -> p.getId().equals(productId))
                .toList()
                .size() > 0) {
            throw new ProductAlreadyInShoppingCartException(productId, username);
        }
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
