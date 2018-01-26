package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ShippingPolicy.
 */
@Entity
@Table(name = "shipping_policy")
public class ShippingPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "shippingPolicy")
    @JsonIgnore
    private Set<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists = new HashSet<>();

    @OneToMany(mappedBy = "shippingPolicy")
    @JsonIgnore
    private Set<MarketSeasonVarietyProperty> marketSeasonVarietyProperties = new HashSet<>();

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public ShippingPolicy shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public ShippingPolicy fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<MarketVarietyPropertyPriceList> getMarketVarietyPropertyPriceLists() {
        return marketVarietyPropertyPriceLists;
    }

    public ShippingPolicy marketVarietyPropertyPriceLists(Set<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists) {
        this.marketVarietyPropertyPriceLists = marketVarietyPropertyPriceLists;
        return this;
    }

    public ShippingPolicy addMarketVarietyPropertyPriceList(MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) {
        this.marketVarietyPropertyPriceLists.add(marketVarietyPropertyPriceList);
        marketVarietyPropertyPriceList.setShippingPolicy(this);
        return this;
    }

    public ShippingPolicy removeMarketVarietyPropertyPriceList(MarketVarietyPropertyPriceList marketVarietyPropertyPriceList) {
        this.marketVarietyPropertyPriceLists.remove(marketVarietyPropertyPriceList);
        marketVarietyPropertyPriceList.setShippingPolicy(null);
        return this;
    }

    public void setMarketVarietyPropertiesPriceList(Set<MarketVarietyPropertyPriceList> marketVarietyPropertyPriceLists) {
        this.marketVarietyPropertyPriceLists = marketVarietyPropertyPriceLists;
    }

    public Set<MarketSeasonVarietyProperty> getMarketSeasonVarietyProperties() {
        return marketSeasonVarietyProperties;
    }

    public ShippingPolicy marketSeasonVarietyProperties(Set<MarketSeasonVarietyProperty> marketSeasonVarietyProperties) {
        this.marketSeasonVarietyProperties = marketSeasonVarietyProperties;
        return this;
    }

    public ShippingPolicy addMarketSeasonVarietyProperty(MarketSeasonVarietyProperty marketSeasonVarietyProperty) {
        this.marketSeasonVarietyProperties.add(marketSeasonVarietyProperty);
        marketSeasonVarietyProperty.setShippingPolicy(this);
        return this;
    }

    public ShippingPolicy removeMarketSeasonVarietyProperty(MarketSeasonVarietyProperty marketSeasonVarietyProperty) {
        this.marketSeasonVarietyProperties.remove(marketSeasonVarietyProperty);
        marketSeasonVarietyProperty.setShippingPolicy(null);
        return this;
    }

    public void setMarketSeasonVarietyProperties(Set<MarketSeasonVarietyProperty> marketSeasonVarietyProperties) {
        this.marketSeasonVarietyProperties = marketSeasonVarietyProperties;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ShippingPolicy company(Company company) {
        this.company = company;
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShippingPolicy shippingPolicy = (ShippingPolicy) o;
        if (shippingPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shippingPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShippingPolicy{" +
            "id=" + id +
            ", shortName='" + shortName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", company=" + company +
            '}';
    }
}
