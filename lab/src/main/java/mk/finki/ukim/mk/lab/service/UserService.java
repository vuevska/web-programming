package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.UserFullName;
import mk.finki.ukim.mk.lab.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<User> listAllUsers();
    User register(UserFullName fullName, String username, String password, String repeatPassword, LocalDate dateOfBirth, Role role);
}
