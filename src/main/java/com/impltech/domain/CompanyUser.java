package com.impltech.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.impltech.service.serialize.MarketDeserializer;
import com.impltech.service.serialize.PositionDeserializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompanyUser.
 */
@Entity
@Table(name = "company_user")
public class CompanyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skype")
    private String skype;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "account_email")
    private String accountEmail;

    @Column(name = "work_email")
    private String workEmail;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "whats_up")
    private String whatsUp;

    @Column(name = "office_phone")
    private String officePhone;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private Company company;

    @ManyToMany(mappedBy = "companyUsers")
    @JsonIgnore
    private Set<Pinch> pinches = new HashSet<>();

    @JsonDeserialize(using = PositionDeserializer.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "company_user_position",
               joinColumns = @JoinColumn(name="company_user_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="position_id", referencedColumnName="id"))
    private Set<Position> positions = new HashSet<>();

    @JsonDeserialize(using = MarketDeserializer.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "company_user_market",
               joinColumns = @JoinColumn(name="company_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "market_id", referencedColumnName = "id"))
    private Set<Market> markets = new HashSet<>();

    @ManyToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkype() {
        return skype;
    }

    public CompanyUser skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getFullName() {
        return fullName;
    }

    public CompanyUser fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccountEmail() {
        return  accountEmail;
    }

    public CompanyUser accoutEmail(String accountEmail) {
        this.accountEmail = accountEmail;
        return this;
    }

    public void setAccountEmail (String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public CompanyUser workEmail(String workEmail) {
        this.workEmail = workEmail;
        return this;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public CompanyUser mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public CompanyUser whatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
        return this;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public CompanyUser officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public User getUser() {
        return user;
    }

    public CompanyUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyUser company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public CompanyUser positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public CompanyUser addPositions(Position position) {
        this.positions.add(position);
        position.getCompanyUsers().add(this);
        return this;
    }

    public CompanyUser removePositions(Position position) {
        this.positions.remove(position);
        position.getCompanyUsers().remove(this);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Market> getMarkets() {
        return markets;
    }

    public CompanyUser markets(Set<Market> markets) {
        this.markets = markets;
        return this;
    }

    public CompanyUser addMarkets(Market market) {
        this.markets.add(market);
        market.getCompanyUsers().add(this);
        return this;
    }

    public CompanyUser removeMarkets(Market market) {
        this.markets.remove(market);
        market.getCompanyUsers().remove(this);
        return this;
    }

    public void setMarkets(Set<Market> markets) {
        this.markets = markets;
    }

    public Set<Pinch> getPinches() {
        return pinches;
    }

    public CompanyUser pinches(Set<Pinch> pinches) {
        this.pinches = pinches;
        return this;
    }

    public CompanyUser addPinches(Pinch pinch) {
        this.pinches.add(pinch);
        pinch.getCompanyUsers().add(this);
        return this;
    }

    public CompanyUser removePinches(Pinch pinch) {
        this.pinches.remove(pinch);
        pinch.getCompanyUsers().remove(this);
        return this;
    }

    public void setPinches(Set<Pinch> pinches) {
        this.pinches = pinches;
    }

    public Client getClient() {
        return client;
    }

    public CompanyUser client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyUser companyUser = (CompanyUser) o;
        if (companyUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyUser{" +
            "id=" + getId() +
            ", skype='" + getSkype() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", accountEmail=" + getAccountEmail() + "'" +
            ", workEmail='" + getWorkEmail() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", whatsUp='" + getWhatsUp() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            "}";
    }
}
