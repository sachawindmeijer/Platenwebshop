package com.example.platenwinkel.controllers;

import com.example.platenwinkel.config.AuthenticationResponse;
import com.example.platenwinkel.dtos.AuthenticationRequest;
import com.example.platenwinkel.service.CostumUserDetailsService;
import com.example.platenwinkel.untils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final CostumUserDetailsService userDetailsService;

    private final JwtUtil jwtUtl;


    public AuthenticationController(AuthenticationManager authenticationManager, CostumUserDetailsService userDetailsService, JwtUtil jwtUtl) {
        this.authenticationManager = authenticationManager;

        this.userDetailsService = userDetailsService;
        this.jwtUtl = jwtUtl;
    }


    /*
        Deze methode geeft de principal (basis user gegevens) terug van de ingelogde gebruiker
    */
    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    /*
    Deze methode geeft het JWT token terug wanneer de gebruiker de juiste inloggegevens op geeft.
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Incorrect username or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String jwt = jwtUtl.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
