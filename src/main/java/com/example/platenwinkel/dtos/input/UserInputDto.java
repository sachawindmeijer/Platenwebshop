package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.models.Authority;

import java.util.Set;

public class UserInputDto {

    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;
    public Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
