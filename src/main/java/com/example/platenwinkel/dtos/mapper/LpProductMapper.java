package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.models.LpProduct;

public class LpProductMapper {

    public static LpProduct fromInputDtoToModel(LpProductInputDto lpProductInputDto) {
        LpProduct lp = new LpProduct();

        lp.setArtist(lpProductInputDto.getArtist());
        lp.setAlbum(lpProductInputDto.getAlbum());
        lp.setDescription(lpProductInputDto.getDescription());
        lp.setGenre(lpProductInputDto.getGenre());
        lp.setInStock(lpProductInputDto.getInStock());

        lp.setPriceExclVat(lpProductInputDto.getPriceExclVat());
        lp.setPriceInclVat();

        return lp;
    }


    public static LpProductOutputDto fromModelToOutputDto(LpProduct lpProduct) {
        LpProductOutputDto lpProductOutputDto = new LpProductOutputDto();
        lpProductOutputDto.setId(lpProduct.getId());
        lpProductOutputDto.setArtist(lpProduct.getArtist());
        lpProductOutputDto.setAlbum(lpProduct.getAlbum());
        lpProductOutputDto.setDescription(lpProduct.getDescription());
        lpProductOutputDto.setGenre(lpProduct.getGenre());
        lpProductOutputDto.setInStock(lpProduct.getInStock());

        lpProductOutputDto.setPriceInclVat(lpProduct.getPriceInclVat());
        lpProductOutputDto.setPriceExclVat(lpProduct.getPriceExclVat());


        return lpProductOutputDto;
    }
}
