package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A CargoEmployeePosition.
 */
@Entity
@Table(name = "cargo_employee_position")
public class CargoEmployeePosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    @ManyToMany(mappedBy = "cargoEmployeePositions")
    private Set<CargoEmployee> cargoEmployees = new HashSet<>();

    @Column(name = "name")
    private String name;

    public Set<CargoEmployee> getCargoEmployees() {
        return cargoEmployees;
    }

    public void setCargoEmployees(Set<CargoEmployee> cargoEmployees) {
        this.cargoEmployees = cargoEmployees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CargoEmployeePosition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public CargoEmployeePosition company(Company company) {
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
        CargoEmployeePosition cargoEmployeePosition = (CargoEmployeePosition) o;
        if (cargoEmployeePosition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cargoEmployeePosition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CargoEmployeePosition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
