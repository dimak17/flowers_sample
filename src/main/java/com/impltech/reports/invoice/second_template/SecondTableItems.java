package com.impltech.reports.invoice.second_template;

import java.math.BigDecimal;

/**
 * Created by alex
 */
public class SecondTableItems {

    private Integer boxQuantity;
    private String boxTypes;
    private String varietyTypes;
    private Integer stemsPerBunch;
    private Integer stemsLength;
    private Integer bunchesPerBox;
    private Integer stemsPerBox;
    private BigDecimal pricePerStem;
    private BigDecimal pricePerBox;
    private Integer totalPieces;
    private BigDecimal totalFB;
    private BigDecimal boxCharge;

    public Integer getBoxQuantity() {
        return boxQuantity;
    }

    public void setBoxQuantity(Integer boxQuantity) {
        this.boxQuantity = boxQuantity;
    }

    public String getBoxTypes() {
        return boxTypes;
    }

    public void setBoxTypes(String boxTypes) {
        this.boxTypes = boxTypes;
    }

    public String getVarietyTypes() {
        return varietyTypes;
    }

    public void setVarietyTypes(String varietyTypes) {
        this.varietyTypes = varietyTypes;
    }

    public Integer getStemsPerBunch() {
        return stemsPerBunch;
    }

    public void setStemsPerBunch(Integer stemsPerBunch) {
        this.stemsPerBunch = stemsPerBunch;
    }

    public Integer getStemsLength() {
        return stemsLength;
    }

    public void setStemsLength(Integer stemsLength) {
        this.stemsLength = stemsLength;
    }

    public Integer getBunchesPerBox() {
        return bunchesPerBox;
    }

    public void setBunchesPerBox(Integer bunchesPerBox) {
        this.bunchesPerBox = bunchesPerBox;
    }

    public Integer getStemsPerBox() {
        return stemsPerBox;
    }

    public void setStemsPerBox(Integer stemsPerBox) {
        this.stemsPerBox = stemsPerBox;
    }

    public BigDecimal getPricePerStem() {
        return pricePerStem;
    }

    public void setPricePerStem(BigDecimal pricePerStem) {
        this.pricePerStem = pricePerStem;
    }

    public BigDecimal getPricePerBox() {
        return pricePerBox;
    }

    public void setPricePerBox(BigDecimal pricePerBox) {
        this.pricePerBox = pricePerBox;
    }

    public Integer getTotalPieces() {
        return totalPieces;
    }

    public void setTotalPieces(Integer totalPieces) {
        this.totalPieces = totalPieces;
    }

    public BigDecimal getTotalFB() {
        return totalFB;
    }

    public void setTotalFB(BigDecimal totalFB) {
        this.totalFB = totalFB;
    }

    public BigDecimal getBoxCharge() {
        return boxCharge;
    }

    public void setBoxCharge(BigDecimal boxCharge) {
        this.boxCharge = boxCharge;
    }
}
