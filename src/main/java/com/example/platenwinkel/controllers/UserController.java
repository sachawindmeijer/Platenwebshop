package com.example.platenwinkel.controllers;


import com.example.platenwinkel.dtos.input.AuthorityInputDto;
import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.exceptions.UserNotFoundException;
import com.example.platenwinkel.models.Authority;
import com.example.platenwinkel.service.UserService;
//import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.BadRequestException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserOutputDto>> getUsers() {

        List<UserOutputDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> getUser(@PathVariable String username) {
        UserOutputDto user = userService.getUser(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "")
    public ResponseEntity<String> createKlant(@RequestBody UserInputDto dto) {;
        if (dto.getUsername() == null || dto.getUsername().isEmpty() ||
                dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new BadRequestException("Username and password are required and cannot be empty");
        }

        String newUsername = userService.createUser(dto);

        if (dto.getAuthorities() != null && !dto.getAuthorities().isEmpty()) {
            for (Authority authority : dto.getAuthorities()) {
                userService.addAuthority(newUsername, authority.getAuthority());
            }
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        String successMessage = "Account created successfully " + newUsername;

        return ResponseEntity.created(location).body(successMessage);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserOutputDto> updateKlant(@PathVariable("username") String username, @RequestBody UserOutputDto dto) {
        userService.updateUser(username, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteKlant(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        Set<Authority> authorities = userService.getAuthorities(username);
        return ResponseEntity.ok(authorities);
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody AuthorityInputDto fields) {
        if (fields.authorityName == null || fields.authorityName.isEmpty()) {
            throw new BadRequestException("Authority name is required");
        }
        userService.addAuthority(username, fields.authorityName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}

