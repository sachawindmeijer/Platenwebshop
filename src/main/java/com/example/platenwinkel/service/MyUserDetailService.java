package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.models.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserOutputDto userDto = userService.getUser(username);

        // Zet authorities om naar Spring Security's GrantedAuthority
        List<GrantedAuthority> grantedAuthorities = userDto.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());

        // Retourneer het UserDetails-object
        return new org.springframework.security.core.userdetails.User(
                username,
                userDto.getPassword(),
                grantedAuthorities
        );
    }
}

