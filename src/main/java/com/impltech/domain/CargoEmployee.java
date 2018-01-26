package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CargoEmployee.
 */
@Entity
@Table(name = "cargo_employee")
public class CargoEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "skype")
    private String skype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cargo_agency_id")
    private CargoAgency cargoAgency;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cargo_employee_market",
        joinColumns = @JoinColumn (name = "cargo_employees_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn (name = "markets_id", referencedColumnName="id"))

    Set<Market> markets = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cargo_employee_cargo_employee_position",
               joinColumns = @JoinColumn(name = "cargo_employee_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name = "cargo_employee_position_id", referencedColumnName="id"))
    private Set<CargoEmployeePosition> cargoEmployeePositions = new HashSet<>();

    @ManyToOne
    private Company company;

    public CargoAgency getCargoAgency() {
        return cargoAgency;
    }

    public void setCargoAgency(CargoAgency cargoAgency) {
        this.cargoAgency = cargoAgency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public CargoEmployee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public CargoEmployee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public CargoEmployee officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public CargoEmployee mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSkype() {
        return skype;
    }

    public CargoEmployee skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Set<CargoEmployeePosition> getCargoEmployeePositions() {
        return cargoEmployeePositions;
    }

    public Set<Market> getMarkets() {
        return markets;
    }

    public CargoEmployee markets(Set<Market> markets) {
        this.markets = markets;
        return this;
    }

    public void setMarkets(Set<Market> markets) {
        this.markets = markets;
    }


    public CargoEmployee cargoEmployeePositions(Set<CargoEmployeePosition> cargoEmployeePositions) {
        this.cargoEmployeePositions = cargoEmployeePositions;
        return this;
    }


    public void setCargoEmployeePositions(Set<CargoEmployeePosition> cargoEmployeePositions) {
        this.cargoEmployeePositions = cargoEmployeePositions;
    }

    public Company getCompany() {
        return company;
    }

    public CargoEmployee company(Company company) {
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
        CargoEmployee cargoEmployee = (CargoEmployee) o;
        if (cargoEmployee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cargoEmployee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CargoEmployee{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", skype='" + getSkype() + "'" +
            "}";
    }
}
