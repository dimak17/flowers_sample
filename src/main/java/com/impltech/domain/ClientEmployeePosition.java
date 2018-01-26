package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientEmployeePosition.
 */
@Entity
@Table(name = "client_employee_position")
public class ClientEmployeePosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    @ManyToMany(mappedBy = "clientEmployeePositions")
    @JsonIgnore
    private Set<ClientEmployee> clientEmployees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ClientEmployeePosition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public ClientEmployeePosition company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ClientEmployee> getClientEmployees() {
        return clientEmployees;
    }

    public ClientEmployeePosition clientEmployees(Set<ClientEmployee> clientEmployees) {
        this.clientEmployees = clientEmployees;
        return this;
    }

    public ClientEmployeePosition addClientEmployee(ClientEmployee clientEmployee) {
        this.clientEmployees.add(clientEmployee);
        clientEmployee.getClientEmployeePositions().add(this);
        return this;
    }

    public ClientEmployeePosition removeClientEmployee(ClientEmployee clientEmployee) {
        this.clientEmployees.remove(clientEmployee);
        clientEmployee.getClientEmployeePositions().remove(this);
        return this;
    }

    public void setClientEmployees(Set<ClientEmployee> clientEmployees) {
        this.clientEmployees = clientEmployees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientEmployeePosition clientEmployeePosition = (ClientEmployeePosition) o;
        if (clientEmployeePosition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientEmployeePosition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientEmployeePosition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
