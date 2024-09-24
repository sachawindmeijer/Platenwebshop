package com.example.platenwinkel.config;

public class AuthenticationResponse {

    //    De AuthenticationResponse klasse wordt gebruikt om een reactie
//    terug te sturen naar de client nadat een gebruiker
//    succesvol is geauthenticeerd.

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
