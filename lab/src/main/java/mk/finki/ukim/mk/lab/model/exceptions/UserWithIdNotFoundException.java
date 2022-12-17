package mk.finki.ukim.mk.lab.model.exceptions;

public class UserWithIdNotFoundException extends RuntimeException{
    public UserWithIdNotFoundException(Long userId) {
        super(String.format("User with id %d does not exist exception", userId));
    }
}
