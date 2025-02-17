package com.example.springWebshop.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.springWebshop.service.UserService;
import com.example.springWebshop.model.User;

import java.util.List;

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
}
