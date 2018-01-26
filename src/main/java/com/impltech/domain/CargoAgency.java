package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CargoAgency.
 */
@Entity
@Table(name = "cargo_agency")
public class CargoAgency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "main_address")
    private String mainAddress;

    @Column(name = "additional_address")
    private String additionalAddress;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "web_page")
    private String webPage;

    @OneToMany(mappedBy = "cargoAgency")
    @JsonIgnore
    private Set<CargoEmployee> cargoEmployees = new HashSet<>();

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CargoAgency name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebPage() {
        return webPage;
    }

    public CargoAgency webPage(String webPage) {
        this.webPage = webPage;
        return this;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public CargoAgency mainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
        return this;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public CargoAgency additionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
        return this;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public CargoAgency officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public CargoAgency email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<CargoEmployee> getCargoEmployees() {
        return cargoEmployees;
    }

    public CargoAgency cargoEmployees(Set<CargoEmployee> cargoEmployees) {
        this.cargoEmployees = cargoEmployees;
        return this;
    }

    public void setCargoEmployees(Set<CargoEmployee> cargoEmployees) {
        this.cargoEmployees = cargoEmployees;
    }

    public Company getCompany() {
        return company;
    }

    public CargoAgency company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CargoAgency cargoAgency = (CargoAgency) o;
        if (cargoAgency.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cargoAgency.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CargoAgency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mainAddress='" + getMainAddress() + "'" +
            ", additionalAddress='" + getAdditionalAddress() + "'" +
            ", phone='" + getOfficePhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", webPage='" + getWebPage() + "'" +
            "}";
    }
}
