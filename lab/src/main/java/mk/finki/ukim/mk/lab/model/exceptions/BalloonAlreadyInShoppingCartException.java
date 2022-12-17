package mk.finki.ukim.mk.lab.model.exceptions;

public class BalloonAlreadyInShoppingCartException extends RuntimeException{
    public BalloonAlreadyInShoppingCartException(Long id, String username) {
        super(String.format("Shopping cart with id %d from user with username %s already exists exception",
                id, username));
    }
}
