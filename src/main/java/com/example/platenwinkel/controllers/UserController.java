package com.example.platenwinkel.controllers;


import com.example.platenwinkel.dtos.input.AuthorityInputDto;
import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.UserNotFoundException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.models.Authority;
import com.example.platenwinkel.service.UserService;
//import org.apache.coyote.BadRequestException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import org.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.BadRequestException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        Set<Authority> authorities = userService.getAuthorities(username);
        return ResponseEntity.ok(authorities);
    }

    @PostMapping(value = "")
    public ResponseEntity<String> createKlant(@Valid @RequestBody UserInputDto dto, BindingResult bindingResult) {;
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
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
    public ResponseEntity<Void> updateUser(
            @PathVariable("username") String username,
            @Valid @RequestBody UserInputDto dto) {


        // Update de gebruiker via de service
        userService.updateUser(username, dto);
        return ResponseEntity.noContent().build(); // 204 No Content bij succesvolle update
    }


    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteKlant(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

}

