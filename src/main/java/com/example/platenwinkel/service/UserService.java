package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.UserInputDto;
import com.example.platenwinkel.dtos.output.UserOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.Authority;

import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.UserRepository;

import com.example.platenwinkel.untils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        return userRepository.findById(username)
                .map(UserService::fromUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }


    public String createUser(UserInputDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);

        User newUser = userRepository.save(toUser(userDto));
        return newUser.getUsername();
    }


    public void updateUser(String username, UserInputDto newUser) {

        validateUserInput(newUser);


        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User not found: " + username));


        updateUserData(user, newUser);


        userRepository.save(user);
    }

    private void validateUserInput(UserInputDto newUser) {
        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new InvalidInputException("Invalid input: password cannot be null or empty");
        }
        if (newUser.getPassword().length() < 8) {
            throw new InvalidInputException("Invalid input: password must be at least 8 characters long");
        }
    }

    private void updateUserData(User user, UserInputDto newUser) {
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        user.setEnabled(newUser.enabled != null && newUser.enabled);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserOutputDto userOutputDto = fromUser(user);
        return userOutputDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) {
            throw new RecordNotFoundException("User with username " + username + " not found");
        }
        userRepository.deleteById(username);
    }

    public static UserOutputDto fromUser(User user) {

        var dto = new UserOutputDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserInputDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }

}