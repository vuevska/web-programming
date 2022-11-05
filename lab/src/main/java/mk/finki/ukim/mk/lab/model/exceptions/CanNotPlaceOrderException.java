package mk.finki.ukim.mk.lab.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotPlaceOrderException extends RuntimeException{
    public CanNotPlaceOrderException() {
        super("Can not complete order!");
    }
}
