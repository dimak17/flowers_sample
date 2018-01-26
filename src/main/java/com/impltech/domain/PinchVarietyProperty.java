package com.impltech.domain;


import com.impltech.domain.enumeration.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PinchVarietyProperty.
 */
@Entity
@Table(name = "pinch_variety_property")
public class PinchVarietyProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "length")
    private Length length;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    private PinchVariety pinchVariety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Length getLength() {
        return length;
    }

    public PinchVarietyProperty length(Length length) {
        this.length = length;
        return this;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public Long getQuantity() {
        return quantity;
    }

    public PinchVarietyProperty quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public PinchVariety getPinchVariety() {
        return pinchVariety;
    }

    public PinchVarietyProperty pinchVariety(PinchVariety pinchVariety) {
        this.pinchVariety = pinchVariety;
        return this;
    }

    public void setPinchVariety(PinchVariety pinchVariety) {
        this.pinchVariety = pinchVariety;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PinchVarietyProperty pinchVarietyProperty = (PinchVarietyProperty) o;
        if (pinchVarietyProperty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinchVarietyProperty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinchVarietyProperty{" +
            "id=" + getId() +
            ", length='" + getLength() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}
