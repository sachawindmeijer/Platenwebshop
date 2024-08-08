package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.helper.PriceCalculator;
import com.example.platenwinkel.service.LpProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/lpproducts")
public class LpProductController {


    private final LpProductService lpProductService;

    public LpProductController(LpProductService lpProductService) {
        this.lpProductService = lpProductService;
    }


    @PostMapping
    public ResponseEntity<LpProductOutputDto> addLpProduct(@Valid @RequestBody LpProductInputDto lpProductInputDto, BindingResult bindingResult) {
        //convert this part to a helper class
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        LpProductOutputDto lpProduct = lpProductService.addLpProduct(lpProductInputDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + lpProduct.getId()).toUriString());


        return ResponseEntity.created(uri).body(lpProduct);
    }


    @GetMapping
    public ResponseEntity<List<LpProductOutputDto>> getAllLps(@RequestParam(value = "artist", required = false) Optional<String> artist) {
        List<LpProductOutputDto> dtos;

        if (artist.isEmpty()) {
            dtos = lpProductService.getAllLps();
        } else {
            dtos = lpProductService.getAllLpProductsByArtist(artist.get());
        }
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LpProductOutputDto> getLpProduct(@PathVariable("id") Long id) {

        LpProductOutputDto lpProduct = lpProductService.getLpProductById(id);
        return ResponseEntity.ok().body(lpProduct);
    }
//    @GetMapping("/search")
//    public ResponseEntity<List<LpProduct>> getLpProductByArtistName(@RequestParam String artist) {
//        return ResponseEntity.ok(lpProductRepository.findAllLpProductsByArtistEqualsIgnoreCase(artist));
//    }
//
//

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLpProduct(@PathVariable Long id) {

        // Verwijder de lp met het opgegeven id uit de database.
        lpProductService.deletelpproduct(id);

        // Return een 204 status
        return ResponseEntity.noContent().build();
    }
}
