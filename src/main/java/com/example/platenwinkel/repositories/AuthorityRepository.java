package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    boolean existsAuthorityByAuthority(String Author);
}
