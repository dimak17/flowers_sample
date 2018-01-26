package com.impltech.service.dto;

import com.impltech.domain.BoxType;

import java.util.List;

/**
 * Created by platon
 */
public class BoxGroupDTO {
    private Long id;
    private List<BoxType> boxTypes;
    private List<Integer> quantities;

    public BoxGroupDTO() {
    }


    public List<BoxType> getBoxTypes() {
        return boxTypes;
    }

    public void setBoxTypes(List<BoxType> boxTypes) {
        this.boxTypes = boxTypes;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BoxGroupDTO{" +
            "id=" + id +
            ", boxTypes=" + boxTypes +
            ", quantities=" + quantities +
            '}';
    }
}
