package com.impltech.reports.tiquet;

/**
 * @author dima
 */
public class Tiquet {

    private String farmName;
    private String flowerType;
    private String agroQuialityNumber;
    private Integer labelBoxId;
    private String AWB;
    private String HAWB;
    private String label;
    private String agency;
    private String country;
    private String variety;
    private Integer length;
    private Integer bunch;
    private Integer stems;

    public String getFarmName() {
	return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(String flowerType) {
        this.flowerType = flowerType;
    }

    public String getAgroQuialityNumber() {
        return agroQuialityNumber;
    }

    public void setAgroQuialityNumber(String agroQuialityNumber) {
        this.agroQuialityNumber = agroQuialityNumber;
    }

    public Integer getLabelBoxId() {
        return labelBoxId;
    }

    public void setLabelBoxId(Integer labelBoxId) {
        this.labelBoxId = labelBoxId;
    }

    public String getAWB() {
        return AWB;
    }

    public void setAWB(String AWB) {
        this.AWB = AWB;
    }

    public String getHAWB() {
        return HAWB;
    }

    public void setHAWB(String HAWB) {
        this.HAWB = HAWB;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getBunch() {
        return bunch;
    }

    public void setBunch(Integer bunch) {
        this.bunch = bunch;
    }

    public Integer getStems() {
        return stems;
    }

    public void setStems(Integer stems) {
        this.stems = stems;
    }
}
