package com.example.platenwinkel.dtos.input;

import jakarta.validation.constraints.NotBlank;

public class AuthorityInputDto {
    @NotBlank
    public String authorityName;
}
