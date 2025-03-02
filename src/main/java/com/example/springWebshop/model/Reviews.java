package com.example.springWebshop.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

// _____________________________________________________________________________

public class Reviews {

    // 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



// _____________________________________________________________________________
// Explicit no-arg constructor

    public Reviews() {}

// _____________________________________________________________________________
// Lifecycle hooks for timestamps.

// _____________________________________________________________________________
// Getters & Setters

}
