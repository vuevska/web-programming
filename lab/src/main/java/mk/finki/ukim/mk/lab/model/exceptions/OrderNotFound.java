package mk.finki.ukim.mk.lab.model.exceptions;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound(Long id) {
        super(String.format("Order with id %d does not exist exception", id));
    }
}
