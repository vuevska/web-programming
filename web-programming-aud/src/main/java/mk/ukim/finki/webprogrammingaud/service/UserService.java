package mk.ukim.finki.webprogrammingaud.service;

import mk.ukim.finki.webprogrammingaud.model.User;
import mk.ukim.finki.webprogrammingaud.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
