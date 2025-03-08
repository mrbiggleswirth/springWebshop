package com.example.springWebshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springWebshop.model.User;
import com.example.springWebshop.service.UserService;

// _____________________________________________________________________________

@RestController
public class UserController {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

// _____________________________________________________________________________

    // Get all users.
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a user by ID.
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Get a user by email.
    @GetMapping("/user/email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
