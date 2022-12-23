package mk.finki.ukim.mk.lab;

import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.mk.lab.repository.jpa.UserRepository;
import mk.finki.ukim.mk.lab.service.impl.AuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginTest {
    @Mock
    private UserRepository userRepository;
    private AuthenticationServiceImpl service;

    @Before
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        service = new AuthenticationServiceImpl(userRepository);
    }

    @Test
    public void testLoginSuccess() {
        User testUser = new User();
        testUser.setUsername("admin");
        testUser.setPassword("admin");
        Mockito.when(userRepository.findByUsernameAndPassword(testUser.getUsername(), testUser.getPassword()))
                .thenReturn(Optional.of(testUser));
        User result = service.login(testUser.getUsername(), testUser.getPassword());
        Assert.assertEquals(testUser, result);
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(null, "password"));
        Mockito.verify(userRepository, Mockito.never()).findByUsernameAndPassword(null, "password");
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(username, "password"));
        Mockito.verify(userRepository, Mockito.never()).findByUsernameAndPassword(username, "password");
    }

    @Test
    public void testEmptyPassword() {
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentsException.class,
                () -> this.service.login("username", password));
        Mockito.verify(userRepository, Mockito.never()).findByUsernameAndPassword("username", password);
    }

    @Test
    public void testNullPassword() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidArgumentsException.class,
                () -> this.service.login("username", null));
        Mockito.verify(userRepository, Mockito.never()).findByUsernameAndPassword("username", null);
    }
}
