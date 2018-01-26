package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.impltech.domain.enumeration.MarketVarietyType;

/**
 * A MarketVariety.
 */
@Entity
@Table(name = "market_variety")
public class MarketVariety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private MarketVarietyType type;

    @ManyToOne
    private Market market;

    @ManyToOne
    private Variety variety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MarketVarietyType getType() {
        return type;
    }

    public MarketVariety type(MarketVarietyType type) {
        this.type = type;
        return this;
    }

    public void setType(MarketVarietyType type) {
        this.type = type;
    }

    public Market getMarket() {
        return market;
    }

    public MarketVariety market(Market market) {
        this.market = market;
        return this;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Variety getVariety() {
        return variety;
    }

    public MarketVariety variety(Variety variety) {
        this.variety = variety;
        return this;
    }

    public void setVariety(Variety variety) {
        this.variety = variety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketVariety marketVariety = (MarketVariety) o;
        if (marketVariety.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketVariety.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketVariety{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
