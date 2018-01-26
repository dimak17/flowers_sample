package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private Set<LabelCountry> labelCountries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LabelCountry> getLabelCountries() {
        return labelCountries;
    }

    public Country labelCountries(Set<LabelCountry> labelCountries) {
        this.labelCountries = labelCountries;
        return this;
    }

    public Country addLabelCountry(LabelCountry labelCountry) {
        this.labelCountries.add(labelCountry);
        labelCountry.setCountry(this);
        return this;
    }

    public Country removeLabelCountry(LabelCountry labelCountry) {
        this.labelCountries.remove(labelCountry);
        labelCountry.setCountry(null);
        return this;
    }

    public void setLabelCountries(Set<LabelCountry> labelCountries) {
        this.labelCountries = labelCountries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
