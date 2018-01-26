package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PinchVariety.
 */
@Entity
@Table(name = "pinch_variety")
public class PinchVariety implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pinch pinch;

    @ManyToOne
    private Variety variety;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pinch getPinch() {
        return pinch;
    }

    public PinchVariety pinch(Pinch pinch) {
        this.pinch = pinch;
        return this;
    }

    public void setPinch(Pinch pinch) {
        this.pinch = pinch;
    }

    public Variety getVariety() {
        return variety;
    }

    public PinchVariety variety(Variety variety) {
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
        PinchVariety pinchVariety = (PinchVariety) o;
        if (pinchVariety.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pinchVariety.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PinchVariety{" +
            "id=" + getId() +
            "}";
    }
}
