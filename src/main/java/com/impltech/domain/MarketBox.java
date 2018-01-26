package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.impltech.domain.enumeration.BoxSizeUnit;

/**
 * A MarketBox.
 */
@Entity
@Table(name = "market_box")
public class MarketBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "length")
    private Integer length;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private BoxSizeUnit unit;

    @ManyToOne
    @JoinColumn(name = "market_id", referencedColumnName = "id")
    @JsonBackReference
    private Market market;

    @ManyToOne
    @JoinColumn(name = "box_type_id", referencedColumnName = "id")
    private BoxType boxType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public MarketBox width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public MarketBox height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLength() {
        return length;
    }

    public MarketBox length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public BoxSizeUnit getUnit() {
        return unit;
    }

    public MarketBox unit(BoxSizeUnit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(BoxSizeUnit unit) {
        this.unit = unit;
    }

    public Market getMarket() {
        return market;
    }

    public MarketBox market(Market market) {
        this.market = market;
        return this;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public MarketBox boxType(BoxType boxType) {
        this.boxType = boxType;
        return this;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketBox marketBox = (MarketBox) o;
        if (marketBox.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketBox.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketBox{" +
            "id=" + getId() +
            ", width='" + getWidth() + "'" +
            ", height='" + getHeight() + "'" +
            ", length='" + getLength() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
