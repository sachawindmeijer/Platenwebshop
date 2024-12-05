package com.example.platenwinkel.service;

import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//Manueel een admin-account aanmaken via de database:
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository. findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
