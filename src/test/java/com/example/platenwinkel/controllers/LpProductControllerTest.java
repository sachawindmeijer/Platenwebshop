package com.example.platenwinkel.controllers;

import com.example.platenwinkel.service.LpProductService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class LpProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void addLpProduct() throws Exception {

        String requestJson = """
                {
                "artist": "The Beatles",
                "album": "Abbey Road",
                "description": "Classic album from 1969",
                "genre": "ROCK",
                "inStock": 10,
                "priceExclVat": 16.80
                }""";

        //act
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/lpproducts")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //assert
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String createdId = jsonNode.get("id").asText();


        assertThat(result.getResponse().getHeader("Location"), matchesPattern("^.*/lpproducts/" + createdId));


        assertEquals("The Beatles", jsonNode.get("artist").asText(), "Artist should match");
        assertEquals("Abbey Road", jsonNode.get("album").asText(), "Album should match");
        assertEquals("Classic album from 1969", jsonNode.get("description").asText(), "Description should match");
        assertEquals("ROCK", jsonNode.get("genre").asText(), "Genre should match");
        assertEquals(10, jsonNode.get("inStock").asInt(), "InStock should match");
        assertEquals(16.80, jsonNode.get("priceExclVat").asDouble(), "PriceEclVat should match");

        assertNotNull(createdId, "ID should not be null");

    }

    @Test
    void updateLpProduct() throws Exception {
        // Arrange
        Long productId = 3L; // Bestaande ID van een product dat in de testdata of een mock zit
        String updateJson = """
                {
                "artist": "St. Vincent",
                "album": "Masseduction",
                "description": "Electropop album by a icon",
                "genre": "ROCK",
                "inStock": 25,
                "priceExclVat": 30.00
                }""";

        // Act
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.put("/lpproducts/" + productId)
                        .contentType(APPLICATION_JSON)
                        .content(updateJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        assertEquals("St. Vincent", jsonNode.get("artist").asText(), "Artist should be updated");
        assertEquals("Masseduction", jsonNode.get("album").asText(), "Album should be updated");
        assertEquals("Electropop album by a icon", jsonNode.get("description").asText(), "Description should be updated");
        assertEquals("ROCK", jsonNode.get("genre").asText(), "Genre should be updated");
        assertEquals(25, jsonNode.get("inStock").asInt(), "InStock should be updated");
        assertEquals(30, jsonNode.get("priceExclVat").asDouble(), "PriceEclVat should be updated");
    }

    @Test
    void getAllLpProducts() throws Exception {
        // Act:
        this.mockMvc.perform(get("/lpproducts"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4))); // +1 als je boven de stappen uitvoert
    }
}











