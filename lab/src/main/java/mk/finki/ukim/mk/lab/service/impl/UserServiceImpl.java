package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }
}
