package mk.ukim.finki.webprogrammingaud.service.impl;

import mk.ukim.finki.webprogrammingaud.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.webprogrammingaud.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.webprogrammingaud.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.webprogrammingaud.model.User;
import mk.ukim.finki.webprogrammingaud.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.webprogrammingaud.repository.impl.InMemoryUserRepository;
import mk.ukim.finki.webprogrammingaud.repository.jpa.UserRepository;
import mk.ukim.finki.webprogrammingaud.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository
                .findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
