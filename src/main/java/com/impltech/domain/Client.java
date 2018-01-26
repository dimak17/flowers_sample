package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address")
    private String address;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "skype")
    private String skype;

    @Column(name = "web_page")
    private String webPage;

    @Column(name = "activation_status")
    private Boolean activationStatus;

    @Column(name = "block_status")
    private Boolean blockStatus;

    @Column(name = "id_number")
    private String idNumber;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<ClientEmployee> clientEmployees = new HashSet<>();

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<MarketClient> marketClients = new HashSet<>();

    @ManyToOne
    private ClaimsPolicy claimsPolicy;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Label> labels = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<CompanyUser> companyUsers = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<ClientPaymentPolicy> clientPaymentPolicies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Client companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public Client address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public Client officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public Client skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getWebPage() {
        return webPage;
    }

    public Client webPage(String webPage) {
        this.webPage = webPage;
        return this;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public Boolean isActivationStatus() {
        return activationStatus;
    }

    public Client activationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
        return this;
    }

    public void setActivationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Boolean isBlockStatus() {
        return blockStatus;
    }

    public Client blockStatus(Boolean blockStatus) {
        this.blockStatus = blockStatus;
        return this;
    }

    public void setBlockStatus(Boolean blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public Client idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Set<ClientEmployee> getClientEmployees() {
        return clientEmployees;
    }

    public Client clientEmployees(Set<ClientEmployee> clientEmployees) {
        this.clientEmployees = clientEmployees;
        return this;
    }

    public Client addClientEmployees(ClientEmployee clientEmployee) {
        this.clientEmployees.add(clientEmployee);
        clientEmployee.setClient(this);
        return this;
    }

    public Client removeClientEmployees(ClientEmployee clientEmployee) {
        this.clientEmployees.remove(clientEmployee);
        clientEmployee.setClient(null);
        return this;
    }

    public void setClientEmployees(Set<ClientEmployee> clientEmployees) {
        this.clientEmployees = clientEmployees;
    }

    public Company getCompany() {
        return company;
    }

    public Client company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<MarketClient> getMarketClients() {
        return marketClients;
    }

    public Client marketClients(Set<MarketClient> marketClients) {
        this.marketClients = marketClients;
        return this;
    }

    public Client addMarketClient(MarketClient marketClient) {
        this.marketClients.add(marketClient);
        marketClient.setClient(this);
        return this;
    }

    public Client removeMarketClient(MarketClient marketClient) {
        this.marketClients.remove(marketClient);
        marketClient.setClient(null);
        return this;
    }

    public void setMarketClients(Set<MarketClient> marketClients) {
        this.marketClients = marketClients;
    }

    public ClaimsPolicy getClaimsPolicy() {
        return claimsPolicy;
    }

    public Client claimsPolicy(ClaimsPolicy claimsPolicy) {
        this.claimsPolicy = claimsPolicy;
        return this;
    }

    public void setClaimsPolicy(ClaimsPolicy claimsPolicy) {
        this.claimsPolicy = claimsPolicy;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public Client labels(Set<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Client addLabel(Label label) {
        this.labels.add(label);
        label.setClient(this);
        return this;
    }

    public Client removeLabel(Label label) {
        this.labels.remove(label);
        label.setClient(null);
        return this;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<CompanyUser> getCompanyUsers() {
        return companyUsers;
    }

    public Client companyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
        return this;
    }

    public Client addCompanyUser(CompanyUser companyUser) {
        this.companyUsers.add(companyUser);
        companyUser.setClient(this);
        return this;
    }

    public Client removeCompanyUser(CompanyUser companyUser) {
        this.companyUsers.remove(companyUser);
        companyUser.setClient(null);
        return this;
    }

    public void setCompanyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
    }

    public Set<ClientPaymentPolicy> getClientPaymentPolicies() {
        return clientPaymentPolicies;
    }

    public Client clientPaymentPolicies(Set<ClientPaymentPolicy> clientPaymentPolicies) {
        this.clientPaymentPolicies = clientPaymentPolicies;
        return this;
    }

    public Client addClientPaymentPolicy(ClientPaymentPolicy clientPaymentPolicy) {
        this.clientPaymentPolicies.add(clientPaymentPolicy);
        clientPaymentPolicy.setClient(this);
        return this;
    }

    public Client removeClientPaymentPolicy(ClientPaymentPolicy clientPaymentPolicy) {
        this.clientPaymentPolicies.remove(clientPaymentPolicy);
        clientPaymentPolicy.setClient(null);
        return this;
    }

    public void setClientPaymentPolicies(Set<ClientPaymentPolicy> clientPaymentPolicies) {
        this.clientPaymentPolicies = clientPaymentPolicies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", address='" + getAddress() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", skype='" + getSkype() + "'" +
            ", webPage='" + getWebPage() + "'" +
            ", activationStatus='" + isActivationStatus() + "'" +
            ", blockStatus='" + isBlockStatus() + "'" +
            ", idNumber='" + getIdNumber() + "'" +
            "}";
    }
}
