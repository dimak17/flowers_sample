package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.impltech.domain.enumeration.PriceListType;

/**
 * A PriceList.
 */
@Entity
@Table(name = "price_list")
public class PriceList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_list_type")
    private PriceListType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceListType getType() {
        return type;
    }

    public PriceList type(PriceListType type) {
        this.type = type;
        return this;
    }

    public void setType(PriceListType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceList priceList = (PriceList) o;
        if (priceList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceList{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
