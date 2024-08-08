package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.LpProductInputDto;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.models.LpProduct;

public class LpProductMapper {
//transferToLpProduct
    public static LpProduct fromInputDtoToModel(LpProductInputDto lpProductInputDto) {
        LpProduct lp = new LpProduct();

        lp.setArtist(lpProductInputDto.artist);
        lp.setAlbum(lpProductInputDto.album);
        lp.setDescription(lpProductInputDto.description);
        lp.setGenre(lpProductInputDto.genre);
        lp.setInStock(lpProductInputDto.inStock);
        lp.setPriceEclVat(lpProductInputDto.priceEclVat);
        lp.setPriceInclVat(lpProductInputDto.PriceInclVat);//in de LpProduct zit de calculatehelper

        return lp;
    }

    //transferToDto
    public static LpProductOutputDto fromModelToOutputDto(LpProduct lpProduct) {
        LpProductOutputDto lpProductOutputDto = new LpProductOutputDto();
        //lpProductOutputDto.setId(lpProduct.getId());// chatgpt zegt dat hier een de id moet om geen null te krijgen
        lpProductOutputDto.setArtist(lpProduct.getArtist());
        lpProductOutputDto.setAlbum(lpProduct.getAlbum());
        lpProductOutputDto.setDescription(lpProduct.getDescription());
        lpProductOutputDto.setGenre(lpProduct.getGenre());
        lpProductOutputDto.setInStock(lpProduct.getInStock());



        return lpProductOutputDto;
    }
}
