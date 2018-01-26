package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BoxGroup.
 */
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id",
    scope = BoxGroup.class
)
@Entity
@Table(name = "box_group")
public class BoxGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "boxGroup", fetch = FetchType.EAGER)
    private Set<BoxTypeGroup> boxTypeGroups = new HashSet<>();

    @ManyToMany(mappedBy = "boxGroups")
    @JsonIgnore
    private Set<Market> markets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public BoxGroup company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public Set<BoxTypeGroup> getBoxTypeGroups() {
        return boxTypeGroups;
    }

    public BoxGroup boxTypeGroups(Set<BoxTypeGroup> boxTypeGroups) {
        this.boxTypeGroups = boxTypeGroups;
        return this;
    }

    public BoxGroup addBoxTypeGroups(BoxTypeGroup boxTypeGroup) {
        this.boxTypeGroups.add(boxTypeGroup);
        boxTypeGroup.setBoxGroup(this);
        return this;
    }

    public BoxGroup removeBoxTypeGroups(BoxTypeGroup boxTypeGroup) {
        this.boxTypeGroups.remove(boxTypeGroup);
        boxTypeGroup.setBoxGroup(null);
        return this;
    }

    public void setBoxTypeGroups(Set<BoxTypeGroup> boxTypeGroups) {
        this.boxTypeGroups = boxTypeGroups;
    }

    public Set<Market> getMarkets() {
        return markets;
    }

    public BoxGroup markets(Set<Market> markets) {
        this.markets = markets;
        return this;
    }

    public BoxGroup addMarkets(Market market) {
        this.markets.add(market);
        market.getBoxGroups().add(this);
        return this;
    }

    public BoxGroup removeMarkets(Market market) {
        this.markets.remove(market);
        market.getBoxGroups().remove(this);
        return this;
    }

    public void setMarkets(Set<Market> markets) {
        this.markets = markets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoxGroup boxGroup = (BoxGroup) o;
        if (boxGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boxGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoxGroup{" +
            "id=" + id +
            ", company=" + company +
            ", boxTypeGroups=" + boxTypeGroups +
            ", markets=" + markets +
            '}';
    }
}
