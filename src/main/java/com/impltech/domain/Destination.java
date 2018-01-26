package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Destination.
 */
@Entity
@Table(name = "destination")
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "info")
    private String info;

    @ManyToOne
    private LabelCountry labelCountry;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public Destination city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInfo() {
        return info;
    }

    public Destination info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LabelCountry getLabelCountry() {
        return labelCountry;
    }

    public Destination labelCountry(LabelCountry labelCountry) {
        this.labelCountry = labelCountry;
        return this;
    }

    public void setLabelCountry(LabelCountry labelCountry) {
        this.labelCountry = labelCountry;
    }

    public Company getCompany() {
        return company;
    }

    public Destination company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Destination destination = (Destination) o;
        if (destination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), destination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Destination{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
