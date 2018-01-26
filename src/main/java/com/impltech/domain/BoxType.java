package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BoxType.
 */
@Entity
@Table(name = "box_type")
public class BoxType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "box_size")
    private String boxSize;

    @ManyToOne
    private Company company;

    //TODO Fix to LAZY
    @OneToMany(mappedBy = "boxType", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<MarketBox> marketBoxes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public BoxType shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public BoxType fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBoxSize() {
        return boxSize;
    }

    public BoxType boxSize(String boxSize) {
        this.boxSize = boxSize;
        return this;
    }

    public void setBoxSize(String boxSize) {
        this.boxSize = boxSize;
    }

    public Company getCompany() {
        return company;
    }

    public BoxType company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<MarketBox> getMarketBoxes() {
        return marketBoxes;
    }

    public BoxType marketBoxes(Set<MarketBox> marketBoxes) {
        this.marketBoxes = marketBoxes;
        return this;
    }

    public BoxType addMarketBoxes(MarketBox marketBox) {
        this.marketBoxes.add(marketBox);
        marketBox.setBoxType(this);
        return this;
    }

    public BoxType removeMarketBoxes(MarketBox marketBox) {
        this.marketBoxes.remove(marketBox);
        marketBox.setBoxType(null);
        return this;
    }

    public void setMarketBoxes(Set<MarketBox> marketBoxes) {
        this.marketBoxes = marketBoxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoxType boxType = (BoxType) o;
        if (boxType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boxType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoxType{" +
            "id=" + id +
            ", shortName='" + shortName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", boxSize='" + boxSize + '\'' +
            ", company=" + company +
            ", marketBoxes=" + marketBoxes +
            '}';
    }
}
