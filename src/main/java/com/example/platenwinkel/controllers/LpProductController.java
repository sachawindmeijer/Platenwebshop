package com.example.platenwinkel.controllers;


import com.example.platenwinkel.repositories.LpProductRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/televisions")
public class LpProductController {


private final LpProductRepository lpProductRepository;

    public LpProductController(LpProductRepository lpProductRepository) {
        this.lpProductRepository = lpProductRepository;
    }


//
//


}
