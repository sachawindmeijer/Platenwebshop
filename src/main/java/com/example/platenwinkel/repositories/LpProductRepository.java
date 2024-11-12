package com.example.platenwinkel.repositories;

import com.example.platenwinkel.models.LpProduct;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LpProductRepository extends JpaRepository<LpProduct, Long> {
 List<LpProduct> findAllLpProductsByArtistEqualsIgnoreCase(String artist );
}
