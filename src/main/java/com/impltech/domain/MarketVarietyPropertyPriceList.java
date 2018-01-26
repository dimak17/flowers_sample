package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.impltech.domain.enumeration.Length;

/**
 * A MarketVarietyPropertyPriceList.
 */
@Entity
@Table(name = "market_variety_property_price_list")
public class MarketVarietyPropertyPriceList implements Serializable {

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
    private PriceList priceList;

    @ManyToOne
    private Market market;

    @ManyToOne
    private Variety variety;

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

    public MarketVarietyPropertyPriceList length(Length length) {
        this.length = length;
        return this;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MarketVarietyPropertyPriceList price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public MarketVarietyPropertyPriceList priceList(PriceList priceList) {
        this.priceList = priceList;
        return this;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public Market getMarket() {
        return market;
    }

    public MarketVarietyPropertyPriceList market(Market market) {
        this.market = market;
        return this;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Variety getVariety() {
        return variety;
    }

    public MarketVarietyPropertyPriceList variety(Variety variety) {
        this.variety = variety;
        return this;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    public ShippingPolicy getShippingPolicy() {
        return shippingPolicy;
    }

    public MarketVarietyPropertyPriceList shippingPolicy(ShippingPolicy shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
        return this;
    }

    public void setShippingPolicy(ShippingPolicy shippingPolicy) {
        this.shippingPolicy = shippingPolicy;
    }

    public Company getCompany() {
        return company;
    }

    public MarketVarietyPropertyPriceList company(Company company) {
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
        MarketVarietyPropertyPriceList marketVarietyPropertyPriceList = (MarketVarietyPropertyPriceList) o;
        if (marketVarietyPropertyPriceList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketVarietyPropertyPriceList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketVarietyPropertyPriceList{" +
            "id=" + getId() +
            ", length='" + getLength() + "'" +
            ", price='" + getPrice() + "'" +
            ", company='" + getCompany() + "'" +
            "}";
    }
}
