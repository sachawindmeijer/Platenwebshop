package com.example.platenwinkel.dtos;

public class AuthenticationRequest {

//    Deze klasse wordt vaak gebruikt als DTO (Data Transfer Object)
//    om de inloggegevens van de gebruiker over te dragen van de client
//    (bijvoorbeeld een webbrowser of mobiele app) naar
//    de server (Spring Boot backend) tijdens het authenticatieproces.

    private String username;
    private String password;

    public AuthenticationRequest() {
    }
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
}
