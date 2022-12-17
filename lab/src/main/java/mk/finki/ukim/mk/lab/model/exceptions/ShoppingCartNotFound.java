package mk.finki.ukim.mk.lab.model.exceptions;

public class ShoppingCartNotFound extends RuntimeException{
    public ShoppingCartNotFound(Long id) {
        super(String.format("Shopping cart with id %d was not found exception", id));
    }
}
