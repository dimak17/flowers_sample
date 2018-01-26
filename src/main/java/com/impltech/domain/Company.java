package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.impltech.domain.enumeration.TYPE_OF_FLOWERS;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farm_name")
    private String farmName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "general_office_phone")
    private String generalOfficePhone;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "farm_size")
    private String farmSize;

    @Column(name = "general_email_address")
    private String generalEmailAddress;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_flowers")
    private TYPE_OF_FLOWERS typeOfFlowers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFarmName() {
        return farmName;
    }

    public Company farmName(String farmName) {
        this.farmName = farmName;
        return this;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getLegalName() {
        return legalName;
    }

    public Company legalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getGeneralOfficePhone() {
        return generalOfficePhone;
    }

    public Company generalOfficePhone(String generalOfficePhone) {
        this.generalOfficePhone = generalOfficePhone;
        return this;
    }

    public void setGeneralOfficePhone(String generalOfficePhone) {
        this.generalOfficePhone = generalOfficePhone;
    }

    public String getCountry() {
        return country;
    }

    public Company country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Company city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFarmSize() {
        return farmSize;
    }

    public Company farmSize(String farmSize) {
        this.farmSize = farmSize;
        return this;
    }

    public void setFarmSize(String farmSize) {
        this.farmSize = farmSize;
    }

    public String getGeneralEmailAddress() {
        return generalEmailAddress;
    }

    public Company generalEmailAddress(String generalEmailAddress) {
        this.generalEmailAddress = generalEmailAddress;
        return this;
    }

    public void setGeneralEmailAddress(String generalEmailAddress) {
        this.generalEmailAddress = generalEmailAddress;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TYPE_OF_FLOWERS getTypeOfFlowers() {
        return typeOfFlowers;
    }

    public Company typeOfFlowers(TYPE_OF_FLOWERS typeOfFlowers) {
        this.typeOfFlowers = typeOfFlowers;
        return this;
    }

    public void setTypeOfFlowers(TYPE_OF_FLOWERS typeOfFlowers) {
        this.typeOfFlowers = typeOfFlowers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", farmName='" + getFarmName() + "'" +
            ", legalName='" + getLegalName() + "'" +
            ", generalOfficePhone='" + getGeneralOfficePhone() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", farmSize='" + getFarmSize() + "'" +
            ", generalEmailAddress='" + getGeneralEmailAddress() + "'" +
            ", address='" + getAddress() + "'" +
            ", typeOfFlowers='" + getTypeOfFlowers() + "'" +
            "}";
    }
}
