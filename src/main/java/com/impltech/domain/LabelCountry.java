package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LabelCountry.
 */
@Entity
@Table(name = "label_country")
public class LabelCountry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Label label;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "labelCountry")
    @JsonIgnore
    private Set<Destination> destinations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public LabelCountry label(Label label) {
        this.label = label;
        return this;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Country getCountry() {
        return country;
    }

    public LabelCountry country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Company getCompany() {
        return company;
    }

    public LabelCountry company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public LabelCountry destinations(Set<Destination> destinations) {
        this.destinations = destinations;
        return this;
    }

    public LabelCountry addDestination(Destination destination) {
        this.destinations.add(destination);
        destination.setLabelCountry(this);
        return this;
    }

    public LabelCountry removeDestination(Destination destination) {
        this.destinations.remove(destination);
        destination.setLabelCountry(null);
        return this;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabelCountry labelCountry = (LabelCountry) o;
        if (labelCountry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), labelCountry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LabelCountry{" +
            "id=" + getId() +
            "}";
    }
}
