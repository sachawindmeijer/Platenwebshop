package com.example.platenwinkel.dtos.input;




import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Map;


public class OrderInputDto {

    @NotNull
    public String username;

    @NotNull
    @Size(min = 5, max = 255)
    public String shippingAdress;

    @NotEmpty
    @Size(min = 1)
    public Map<Long, Integer> items; // Mapping product ID's to quantities

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getShippingAdress() {
        return shippingAdress;
    }

    public void setShippingAdress(String shippingAdress) {
        this.shippingAdress = shippingAdress;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }
}
