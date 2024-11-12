package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
