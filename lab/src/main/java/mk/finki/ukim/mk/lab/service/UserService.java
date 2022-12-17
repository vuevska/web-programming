package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> listAllUsers();
}
