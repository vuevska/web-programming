package mk.finki.ukim.mk.lab.model.exceptions;

public class BalloonNotFoundException extends RuntimeException{
    public BalloonNotFoundException(Long id) {
        super(String.format("Balloon with id %d does not exist exception", id));
    }
}
