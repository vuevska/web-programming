package mk.ukim.finki.webprogrammingaud.web.controller;

import mk.ukim.finki.webprogrammingaud.model.enumerations.Role;
import mk.ukim.finki.webprogrammingaud.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.webprogrammingaud.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.webprogrammingaud.service.AuthenticationService;
import mk.ukim.finki.webprogrammingaud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    public RegisterController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }


    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam Role role) {
        try {
            this.userService.register(username, password, repeatedPassword, name, surname, role);
            return "redirect:/login";
        } catch (PasswordsDoNotMatchException |InvalidArgumentsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }

    }
}
