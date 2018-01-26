package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Label.
 */
@Entity
@Table(name = "label")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "delivery_days")
    private Long deliveryDays;

    @Column(name = "wanted")
    private Boolean wanted;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "label")
    @JsonIgnore
    private Set<LabelCountry> labelCountries = new HashSet<>();

    @ManyToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public Label labelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getDeliveryDays() {
        return deliveryDays;
    }

    public Label deliveryDays(Long deliveryDays) {
        this.deliveryDays = deliveryDays;
        return this;
    }

    public void setDeliveryDays(Long deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public Boolean isWanted() {
        return wanted;
    }

    public Label wanted(Boolean wanted) {
        this.wanted = wanted;
        return this;
    }

    public void setWanted(Boolean wanted) {
        this.wanted = wanted;
    }

    public Company getCompany() {
        return company;
    }

    public Label company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<LabelCountry> getLabelCountries() {
        return labelCountries;
    }

    public Label labelCountries(Set<LabelCountry> labelCountries) {
        this.labelCountries = labelCountries;
        return this;
    }

    public Label addLabelCountry(LabelCountry labelCountry) {
        this.labelCountries.add(labelCountry);
        labelCountry.setLabel(this);
        return this;
    }

    public Label removeLabelCountry(LabelCountry labelCountry) {
        this.labelCountries.remove(labelCountry);
        labelCountry.setLabel(null);
        return this;
    }

    public void setLabelCountries(Set<LabelCountry> labelCountries) {
        this.labelCountries = labelCountries;
    }

    public Client getClient() {
        return client;
    }

    public Label client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Label label = (Label) o;
        if (label.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), label.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Label{" +
            "id=" + getId() +
            ", labelName='" + getLabelName() + "'" +
            ", deliveryDays='" + getDeliveryDays() + "'" +
            ", wanted='" + isWanted() + "'" +
            "}";
    }
}
