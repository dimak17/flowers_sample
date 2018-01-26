package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ClientEmployee.
 */
@Entity
@Table(name = "client_employee")
public class ClientEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "skype")
    private String skype;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Company company;

    @ManyToMany
    @JoinTable(name = "client_employee_client_employee_position",
               joinColumns = @JoinColumn(name="client_employees_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="client_employee_positions_id", referencedColumnName="id"))
    private Set<ClientEmployeePosition> clientEmployeePositions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public ClientEmployee fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public ClientEmployee mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public ClientEmployee officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public ClientEmployee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public ClientEmployee skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Client getClient() {
        return client;
    }

    public ClientEmployee client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Company getCompany() {
        return company;
    }

    public ClientEmployee company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ClientEmployeePosition> getClientEmployeePositions() {
        return clientEmployeePositions;
    }

    public ClientEmployee clientEmployeePositions(Set<ClientEmployeePosition> clientEmployeePositions) {
        this.clientEmployeePositions = clientEmployeePositions;
        return this;
    }

    public ClientEmployee addClientEmployeePosition(ClientEmployeePosition clientEmployeePosition) {
        this.clientEmployeePositions.add(clientEmployeePosition);
        clientEmployeePosition.getClientEmployees().add(this);
        return this;
    }

    public ClientEmployee removeClientEmployeePosition(ClientEmployeePosition clientEmployeePosition) {
        this.clientEmployeePositions.remove(clientEmployeePosition);
        clientEmployeePosition.getClientEmployees().remove(this);
        return this;
    }

    public void setClientEmployeePositions(Set<ClientEmployeePosition> clientEmployeePositions) {
        this.clientEmployeePositions = clientEmployeePositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientEmployee clientEmployee = (ClientEmployee) o;
        if (clientEmployee.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientEmployee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientEmployee{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", skype='" + getSkype() + "'" +
            "}";
    }
}
