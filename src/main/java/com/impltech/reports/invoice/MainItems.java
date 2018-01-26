package com.impltech.reports.invoice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author dima
 */
public class MainItems {

  private String companyName;
  private String companyCountry;
  private String companyCity;
  private String companyStreet;
  private String companyBrandName;
  private String companyEmail;
  private String companyPhoneNumber;
  private String companyCelNumber;
  private String companyFax;
  private String coordinationName;

  private Date invoiceDate;
  private Date shipmentDate;
  private String invoiceNumber;

  private String customerName;
  private String customerCountry;
  private String customerCity;
  private String customerStreet;
  private String customerPhone;

  //To-Do List of customerLabels for another format of invoice with a lot of lables!
  private String customerLabel;
  private String labelCountry;
  private String labelCity;
  /*** labelPhone only if needed. */
  private String labelPhone;

  private String agency;
  private String airLine;
  private String AWB;
  private String daughterAWB;

  private List<TableItems> items;
  private BigDecimal totalInvoicePrice;
  private Integer totalStems;
  private Float totalFB;



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public void setCompanyCountry(String companyCountry) {
        this.companyCountry = companyCountry;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyStreet() {
        return companyStreet;
    }

    public void setCompanyStreet(String companyStreet) {
        this.companyStreet = companyStreet;
    }

    public String getCoordinationName() {
        return coordinationName;
    }

    public void setCoordinationName(String coordinationName) {
        this.coordinationName = coordinationName;
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

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyCelNumber() {
        return companyCelNumber;
    }

    public void setCompanyCelNumber(String companyCelNumber) {
        this.companyCelNumber = companyCelNumber;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
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

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getLabelCity() {
        return labelCity;
    }

    public void setLabelCity(String labelCity) {
        this.labelCity = labelCity;
    }

    public String getLabelPhone() {
        return labelPhone;
    }

    public void setLabelPhone(String labelPhone) {
        this.labelPhone = labelPhone;
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

    public String getCustomerStreet() {
        return customerStreet;
    }

    public void setCustomerStreet(String customerStreet) {
        this.customerStreet = customerStreet;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine;
    }

    public String getAWB() {
        return AWB;
    }

    public void setAWB(String AWB) {
        this.AWB = AWB;
    }

    public String getDaughterAWB() {
        return daughterAWB;
    }

    public void setDaughterAWB(String daughterAWB) {
        this.daughterAWB = daughterAWB;
    }

    public List<TableItems> getItems() {
		return items;
	}

	public void setItems(List<TableItems> items) {
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
}
