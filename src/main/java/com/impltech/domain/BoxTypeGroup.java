package com.impltech.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BoxTypeGroup.
 */
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
@Entity
@Table(name = "box_type_group")
public class BoxTypeGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1)
    @Column(name = "quantity")
    private Integer quantity;

    @Min(value = 0)
    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    private BoxGroup boxGroup;

    @ManyToOne
    private BoxType boxType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BoxTypeGroup quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrder() {
        return order;
    }

    public BoxTypeGroup order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public BoxGroup getBoxGroup() {
        return boxGroup;
    }

    public BoxTypeGroup boxGroup(BoxGroup boxGroup) {
        this.boxGroup = boxGroup;
        return this;
    }

    public void setBoxGroup(BoxGroup boxGroup) {
        this.boxGroup = boxGroup;
    }

    public BoxType getBoxType() {
        return boxType;
    }

    public BoxTypeGroup boxType(BoxType boxType) {
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
        BoxTypeGroup boxTypeGroup = (BoxTypeGroup) o;
        if (boxTypeGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boxTypeGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoxTypeGroup{" +
            "id=" + getId() +
            ", quantity='" + getQuantity() + "'" +
            ", order='" + getOrder() + "'" +
            "}";
    }
}
