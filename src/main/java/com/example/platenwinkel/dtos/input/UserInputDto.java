package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.models.Authority;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class UserInputDto {
//    (message = "Username cannot be blank")
    @NotBlank
    @Size(min = 3, max = 50)
    public String username;

    @NotBlank
    @Size(min = 8, message = "Het wachtwoord moet minstens 8 tekens lang zijn")
    public String password;
    public Boolean enabled;
    public String apikey;
    @Email
    @NotBlank
    @Size(max = 100)
    public String email;

    @NotNull
    @Valid
    public Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
