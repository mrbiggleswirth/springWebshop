package com.example.springWebshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springWebshop.repository.UserRepository;
import com.example.springWebshop.model.User;

// _____________________________________________________________________________

@Service
public class UserService {
    /**
     * Constructor injection is preferred over @Autowired on fields.
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        /**
         * Fetches user by ID or returns null.
         */
        return userRepository.findById(id).orElse(null);
    }
}
