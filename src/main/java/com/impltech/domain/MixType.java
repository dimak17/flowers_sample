package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MixType.
 */
@Entity
@Table(name = "mix_type")
public class MixType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MixType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public MixType company(Company company) {
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
        MixType mixType = (MixType) o;
        if (mixType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mixType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MixType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
