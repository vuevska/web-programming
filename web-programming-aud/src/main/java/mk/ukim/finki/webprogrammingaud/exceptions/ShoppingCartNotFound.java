package mk.ukim.finki.webprogrammingaud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShoppingCartNotFound extends RuntimeException{
    public ShoppingCartNotFound(Long id) {
        super(String.format("Shopping cart with id %d was not found", id));
    }
}
