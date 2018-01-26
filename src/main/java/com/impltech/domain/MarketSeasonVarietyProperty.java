package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.impltech.domain.enumeration.Length;

/**
 * A MarketSeasonVarietyProperty.
 */
@Entity
@Table(name = "market_season_variety_property")
public class MarketSeasonVarietyProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "length")
    private Length length;

    @Column(name = "price", precision=10, scale=2)
    private BigDecimal price;

    @ManyToOne
    private Variety variety;

    @ManyToOne
    private PriceList priceList;

    @ManyToOne
    private MarketSeason marketSeason;

    @ManyToOne
    private ShippingPolicy shippingPolicy;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Length getLength() {
        return length;
    }

    public MarketSeasonVarietyProperty length(Length length) {
        this.length = length;
        return this;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MarketSeasonVarietyProperty price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Variety getVariety() {
        return variety;
    }

    public MarketSeasonVarietyProperty variety(Variety variety) {
        this.variety = variety;
        return this;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public MarketSeasonVarietyProperty priceList(PriceList priceList) {
        this.priceList = priceList;
        return this;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public MarketSeason getMarketSeason() {
        return marketSeason;
    }

    public MarketSeasonVarietyProperty marketSeason(MarketSeason marketSeason) {
        this.marketSeason = marketSeason;
        return this;
    }

    public void setMarketSeason(MarketSeason marketSeason) {
        this.marketSeason = marketSeason;
    }

    public ShippingPolicy getShippingPolicy() {
        return shippingPolicy;
    }

    public MarketSeasonVarietyProperty shippingPolicy(ShippingPolicy shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
        return this;
    }

    public void setShippingPolicy(ShippingPolicy shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
    }

    public Company getCompany() {
        return company;
    }

    public MarketSeasonVarietyProperty company(Company company) {
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
        MarketSeasonVarietyProperty marketSeasonVarietyProperty = (MarketSeasonVarietyProperty) o;
        if (marketSeasonVarietyProperty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketSeasonVarietyProperty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketSeasonVarietyProperty{" +
            "id=" + getId() +
            ", length='" + getLength() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
