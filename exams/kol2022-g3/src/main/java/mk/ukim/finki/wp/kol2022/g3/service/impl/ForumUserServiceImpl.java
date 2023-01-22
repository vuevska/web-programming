package mk.ukim.finki.wp.kol2022.g3.service.impl;

import mk.ukim.finki.wp.kol2022.g3.model.ForumUser;
import mk.ukim.finki.wp.kol2022.g3.model.ForumUserType;
import mk.ukim.finki.wp.kol2022.g3.model.Interest;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidForumUserIdException;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidInterestIdException;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidUsernameException;
import mk.ukim.finki.wp.kol2022.g3.repository.ForumUserRepository;
import mk.ukim.finki.wp.kol2022.g3.repository.InterestRepository;
import mk.ukim.finki.wp.kol2022.g3.service.ForumUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ForumUserServiceImpl implements ForumUserService, UserDetailsService {

    private final ForumUserRepository forumUserRepository;
    private final InterestRepository interestRepository;
    private final PasswordEncoder passwordEncoder;

    public ForumUserServiceImpl(ForumUserRepository forumUserRepository, InterestRepository interestRepository, PasswordEncoder passwordEncoder) {
        this.forumUserRepository = forumUserRepository;
        this.interestRepository = interestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ForumUser> listAll() {
        return this.forumUserRepository.findAll();
    }

    @Override
    public ForumUser findById(Long id) {
        return this.forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
    }

    @Override
    public ForumUser create(String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        List<Interest> interests = this.interestRepository.findAllById(interestId);
        String encryptedPassword = this.passwordEncoder.encode(password);
        ForumUser forumUser = new ForumUser(name, email, encryptedPassword, type, interests, birthday);
        this.forumUserRepository.save(forumUser);
        return forumUser;
    }

    @Override
    public ForumUser update(Long id, String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        ForumUser forumUser = this.forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
        forumUser.setName(name);
        forumUser.setEmail(email);
        forumUser.setPassword(password);
        forumUser.setType(type);
        List<Interest> interests = this.interestRepository.findAllById(interestId);
        forumUser.setInterests(interests);
        forumUser.setBirthday(birthday);
        this.forumUserRepository.save(forumUser);
        return forumUser;
    }

    @Override
    public ForumUser delete(Long id) {
        ForumUser forumUser = this.forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
        this.forumUserRepository.delete(forumUser);
        return forumUser;
    }

    @Override
    public List<ForumUser> filter(Long interestId, Integer age) {
        List<ForumUser> users;
        if (interestId != null && age != null) {
            users = this.forumUserRepository.findAllByInterestsContainingAndBirthdayBefore(this.interestRepository.findById(interestId).orElseThrow(InvalidInterestIdException::new),
                    LocalDate.now().minusYears(age));
        } else if(interestId != null) {
            users = this.forumUserRepository.findAllByInterestsContaining(this.interestRepository.findById(interestId).orElseThrow(InvalidInterestIdException::new));
        } else if(age != null) {
            users = this.forumUserRepository.findAllByBirthdayBefore(LocalDate.now().minusYears(age));
        } else {
            users = this.forumUserRepository.findAll();
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ForumUser forumUser = this.forumUserRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(forumUser.getEmail(), forumUser.getPassword(), Stream.of(new SimpleGrantedAuthority("ROLE_"+forumUser.getType().toString())).collect(Collectors.toList()));
    }
}
