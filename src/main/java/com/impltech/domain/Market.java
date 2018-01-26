package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Market.
 */
@Entity
@Table(name = "market")
public class Market implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "market")
    private Set<MarketVariety> marketVarieties = new HashSet<>();

    @ManyToMany(mappedBy = "markets")
    @JsonIgnore
    private  Set<CargoEmployee> cargoEmployees = new HashSet<>();

    public Set<CargoEmployee> getCargoEmployees() {
        return cargoEmployees;
    }

    public void setCargoEmployees(Set<CargoEmployee> cargoEmployees) {
        this.cargoEmployees = cargoEmployees;
    }

    @ManyToMany
    @JoinTable(name = "market_box_groups",
               joinColumns = @JoinColumn(name="markets_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="box_groups_id", referencedColumnName="id"))
    private Set<BoxGroup> boxGroups = new HashSet<>();

    @OneToMany(
        mappedBy = "market",
        cascade = { CascadeType.MERGE, CascadeType.PERSIST },
        fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private Set<MarketBox> marketBoxes = new HashSet<>();

    @ManyToMany(mappedBy = "markets")
    @JsonIgnore
    private Set<CompanyUser> companyUsers = new HashSet<>();

    @OneToMany(mappedBy = "market")
    @JsonIgnore
    private Set<MarketSeason> marketSeasons = new HashSet<>();

    @OneToMany(mappedBy = "market")
    @JsonIgnore
    private Set<MarketClient> marketClients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Market name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public Market company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<MarketVariety> getMarketVarieties() {
        return marketVarieties;
    }

    public Market marketVarieties(Set<MarketVariety> marketVarieties) {
        this.marketVarieties = marketVarieties;
        return this;
    }

    public void setMarketVarieties(Set<MarketVariety> marketVarieties) {
        this.marketVarieties = marketVarieties;
    }

    public Set<BoxGroup> getBoxGroups() {
        return boxGroups;
    }

    public Market boxGroups(Set<BoxGroup> boxGroups) {
        this.boxGroups = boxGroups;
        return this;
    }

    public void setBoxGroups(Set<BoxGroup> boxGroups) {
        this.boxGroups = boxGroups;
    }

    public Set<MarketBox> getMarketBoxes() {
        return marketBoxes;
    }

    public Market marketBoxes(Set<MarketBox> marketBoxes) {
        this.marketBoxes = marketBoxes;
        return this;
    }

    public void setMarketBoxes(Set<MarketBox> marketBoxes) {
        this.marketBoxes = marketBoxes;
    }

    public Set<CompanyUser> getCompanyUsers() {
        return companyUsers;
    }

    public Market companyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
        return this;
    }

    public void setCompanyUsers(Set<CompanyUser> companyUsers) {
        this.companyUsers = companyUsers;
    }

    public Set<MarketSeason> getMarketSeasons() {
        return marketSeasons;
    }

    public Market marketSeasons(Set<MarketSeason> marketSeasons) {
        this.marketSeasons = marketSeasons;
        return this;
    }

    public void setMarketSeasons(Set<MarketSeason> marketSeasons) {
        this.marketSeasons = marketSeasons;
    }

    public Set<MarketClient> getMarketClient() {
        return marketClients;
    }

    public Market marketClient(Set<MarketClient> marketClients) {
        this.marketClients = marketClients;
        return this;
    }

    public void setMarketClients(Set<MarketClient> marketClients) {
        this.marketClients = marketClients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Market market = (Market) o;
        if (market.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), market.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Market{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
