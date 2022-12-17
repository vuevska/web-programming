package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.UserFullName;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidArgumentsException;
import mk.finki.ukim.mk.lab.model.exceptions.PasswordsDoNotMatchException;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthenticationService authenticationService;

    public RegisterController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                               LocalDate dateOfBirth) {
        try {
            UserFullName fullName = new UserFullName();
            fullName.setName(name);
            fullName.setSurname(surname);
            this.authenticationService.register(fullName, username, password, repeatPassword, dateOfBirth);
            return "redirect:/login";
        } catch (PasswordsDoNotMatchException | InvalidArgumentsException e) {
            return "redirect:/register?error=" + e.getMessage();
        }
    }
}
