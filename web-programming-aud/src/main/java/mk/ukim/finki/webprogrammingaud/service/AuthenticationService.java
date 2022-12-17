package mk.ukim.finki.webprogrammingaud.service;

import mk.ukim.finki.webprogrammingaud.model.User;

public interface AuthenticationService {
    User login(String username, String password);
}
