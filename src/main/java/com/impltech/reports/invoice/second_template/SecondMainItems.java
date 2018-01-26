package com.impltech.reports.invoice.second_template;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by alex
 */
public class SecondMainItems {

    //Company
    private String invoiceNumber;
    private Date invoiceDate;
    private Date shipmentDate;

    private String fullName;
    private String companyName;
    private String companyBrandName;
    private String companyAddress;
    private String companyEmail;
    private String companyOfficePhone;
    private String companyMobilePhone;

    //Bill Customer
    private String customerName;
    private String customerCompanyName;
    private String customerCountry;
    private String customerCity;
    private String customerStreet;
    private String customerPhone;
    private String customerID;
    private String customerContactPersonName;

    //Ship Customer
    private String customerLabel;
    private String labelCountry;
    private String labelCity;
    private String labelStreet;
    private String labelPhone;
    private String labelEmail;
    private String labelID;
    private String labelContactPersonName;
    //---------------------------------------
    private String cargoAgency;
    private String airline;
    private String aWB;
    private String hAWB;
    //---------------------------------------
    private List<SecondTableItems> items;
    private BigDecimal totalInvoicePrice;
    private Integer totalStems;
    private Float totalFB;
    //---------------------------------------
    private String Season;
    private Date startSeason;
    private Date endSeason;
    //---------------------------------------
    private String typeOrder;
    //---------------------------------------
    private String market;

    public String getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(Date shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCustomerCompanyName() {
        return customerCompanyName;
    }

    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerStreet() {
        return customerStreet;
    }

    public void setCustomerStreet(String customerStreet) {
        this.customerStreet = customerStreet;
    }

    public String getCompanyBrandName() {
        return companyBrandName;
    }

    public void setCompanyBrandName(String companyBrandName) {
        this.companyBrandName = companyBrandName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyOfficePhone() {
        return companyOfficePhone;
    }

    public void setCompanyOfficePhone(String companyOfficePhone) {
        this.companyOfficePhone = companyOfficePhone;
    }

    public String getCompanyMobilePhone() {
        return companyMobilePhone;
    }

    public void setCompanyMobilePhone(String companyMobilePhone) {
        this.companyMobilePhone = companyMobilePhone;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerLabel() {
        return customerLabel;
    }

    public void setCustomerLabel(String customerLabel) {
        this.customerLabel = customerLabel;
    }

    public String getLabelCountry() {
        return labelCountry;
    }

    public void setLabelCountry(String labelCountry) {
        this.labelCountry = labelCountry;
    }

    public String getLabelPhone() {
        return labelPhone;
    }

    public void setLabelPhone(String labelPhone) {
        this.labelPhone = labelPhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactPersonName() {
        return customerContactPersonName;
    }

    public void setCustomerContactPersonName(String customerContactPersonName) {
        this.customerContactPersonName = customerContactPersonName;
    }

    public String getLabelCity() {
        return labelCity;
    }

    public void setLabelCity(String labelCity) {
        this.labelCity = labelCity;
    }

    public String getLabelStreet() {
        return labelStreet;
    }

    public void setLabelStreet(String labelStreet) {
        this.labelStreet = labelStreet;
    }

    public String getLabelEmail() {
        return labelEmail;
    }

    public void setLabelEmail(String labelEmail) {
        this.labelEmail = labelEmail;
    }

    public String getLabelContactPersonName() {
        return labelContactPersonName;
    }

    public void setLabelContactPersonName(String labelContactPersonName) {
        this.labelContactPersonName = labelContactPersonName;
    }

    public String getLabelID() {
        return labelID;
    }

    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }

    public String getCargoAgency() {
        return cargoAgency;
    }

    public void setCargoAgency(String cargoAgency) {
        this.cargoAgency = cargoAgency;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getaWB() {
        return aWB;
    }

    public void setaWB(String aWB) {
        this.aWB = aWB;
    }

    public String gethAWB() {
        return hAWB;
    }

    public void sethAWB(String hAWB) {
        this.hAWB = hAWB;
    }

    public List<SecondTableItems> getItems() {
        return items;
    }

    public void setItems(List<SecondTableItems> items) {
        this.items = items;
    }

    public BigDecimal getTotalInvoicePrice() {
        return totalInvoicePrice;
    }

    public void setTotalInvoicePrice(BigDecimal totalInvoicePrice) {
        this.totalInvoicePrice = totalInvoicePrice;
    }

    public Integer getTotalStems() {
        return totalStems;
    }

    public void setTotalStems(Integer totalStems) {
        this.totalStems = totalStems;
    }

    public Float getTotalFB() {
        return totalFB;
    }

    public void setTotalFB(Float totalFB) {
        this.totalFB = totalFB;
    }

    public String getSeason() {
        return Season;
    }

    public void setSeason(String season) {
        Season = season;
    }

    public Date getStartSeason() {
        return startSeason;
    }

    public void setStartSeason(Date startSeason) {
        this.startSeason = startSeason;
    }

    public Date getEndSeason() {
        return endSeason;
    }

    public void setEndSeason(Date endSeason) {
        this.endSeason = endSeason;
    }
}
