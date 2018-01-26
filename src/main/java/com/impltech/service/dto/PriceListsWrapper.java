package com.impltech.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.impltech.service.serialize.PriceListTypeDeserializer;

import java.util.List;



public class PriceListsWrapper {

    @JsonDeserialize (using = PriceListTypeDeserializer.class)
    private List<PriceListDTO> priceLists;

    private String priceListType;

    private String extension;

    public PriceListsWrapper() {
    }

    public String getPriceListType() {
        return priceListType;
    }

    public void setPriceListType(String priceListType) {
        this.priceListType = priceListType;
    }

    public List<PriceListDTO> getPriceLists() {
        return priceLists;
    }

    public void setPriceLists(List<PriceListDTO> priceLists) {
        this.priceLists = priceLists;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
