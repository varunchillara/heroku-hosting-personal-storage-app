package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UsersService usersService;

    public SignupController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public String signupView() {

        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute Users user, Model model) {

        String signupError = null;

        if (!usersService.doesUserExist(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = usersService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
            return "login";
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";

    }
}
