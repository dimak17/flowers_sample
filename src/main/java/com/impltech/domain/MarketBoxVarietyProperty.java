package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.impltech.domain.enumeration.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * A MarketBoxVarietyProperty.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "market_box_variety_property")
public class MarketBoxVarietyProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "length")
    private Length length;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private MarketBox marketBox;

    @ManyToOne
    private Variety variety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public MarketBoxVarietyProperty capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Length getLength() {
        return length;
    }

    public MarketBoxVarietyProperty length(Length length) {
        this.length = length;
        return this;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public MarketBox getMarketBox() {
        return marketBox;
    }

    public MarketBoxVarietyProperty marketBox(MarketBox marketBox) {
        this.marketBox = marketBox;
        return this;
    }

    public void setMarketBox(MarketBox marketBox) {
        this.marketBox = marketBox;
    }

    public Variety getVariety() {
        return variety;
    }

    public MarketBoxVarietyProperty variety(Variety variety) {
        this.variety = variety;
        return this;
    }

    public MarketBoxVarietyProperty id(Long id) {
        this.id = id;
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
        MarketBoxVarietyProperty marketBoxVarietyProperty = (MarketBoxVarietyProperty) o;
        if (marketBoxVarietyProperty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketBoxVarietyProperty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketBoxVarietyProperty{" +
            "id=" + getId() +
            ", capacity='" + getCapacity() + "'" +
            ", length='" + getLength() + "'" +
            "}";
    }
}
