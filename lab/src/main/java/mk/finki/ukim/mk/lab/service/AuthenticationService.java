package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.UserFullName;

import java.time.LocalDate;
import java.util.List;

public interface AuthenticationService {

    User login(String username, String password);
    User register(UserFullName fullName, String username, String password, String repeatPassword, LocalDate dateOfBirth);
}
