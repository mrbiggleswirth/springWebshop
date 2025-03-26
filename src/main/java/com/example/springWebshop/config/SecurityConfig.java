package com.example.springWebshop.config;

import com.example.springWebshop.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// _____________________________________________________________________________

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Authentication endpoints.
                .requestMatchers("/auth/**").permitAll()

                // Public product endpoints.
                .requestMatchers("/api/products").permitAll()
                .requestMatchers("/api/products/{id}").permitAll()
                .requestMatchers("/api/products/search").permitAll()
                .requestMatchers("/api/products/category/{categoryId}").permitAll()
                .requestMatchers("/api/products/price").permitAll()

                // For backward compatibility with older endpoints.
                .requestMatchers("/products").permitAll()
                .requestMatchers("/product/{id}").permitAll()
                .requestMatchers("/products/name/{name}").permitAll()

                // Admin endpoints - require authentication.
                .requestMatchers("/api/admin/**").authenticated()

                // Any other request requires authentication.
                .anyRequest().authenticated())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
