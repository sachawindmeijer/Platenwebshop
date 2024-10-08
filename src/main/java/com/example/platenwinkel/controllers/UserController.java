package com.example.platenwinkel.controllers;


import com.example.platenwinkel.dtos.input.AuthorityInputDto;
import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
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
import java.util.List;

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
    public ResponseEntity<UserOutputDto> getUser(@PathVariable("username") String username) {

        UserOutputDto optionalUser = userService.getUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping(value = "")
    public ResponseEntity<UserOutputDto> createKlant(@RequestBody UserInputDto dto) {;

        // Let op: het password van een nieuwe gebruiker wordt in deze code nog niet encrypted opgeslagen.
        // Je kan dus (nog) niet inloggen met een nieuwe user.
//        String newUsername = userService.createUser(dto, passwordEncoder);

        String newUsername = userService.createUser(dto);
        if (dto.getAuthorities() != null && !dto.getAuthorities().isEmpty()) {
            for (Authority authority : dto.getAuthorities()) {
                userService.addAuthority(newUsername, authority.getAuthority());
            }
        }

//        userService.addAuthority(newUsername, "ROLE_USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
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
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody AuthorityInputDto fields) {
        try {

            userService.addAuthority(username, fields.authorityName);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }
}

