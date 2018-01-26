package com.impltech.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ClaimsPolicy.
 */
@Entity
@Table(name = "claims_policy")
public class ClaimsPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "full_name")
    private String fullName;


    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ClaimsPolicy shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ClaimsPolicy company(Company company) {
        this.company = company;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ClaimsPolicy fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClaimsPolicy claimsPolicy = (ClaimsPolicy) o;
        if (claimsPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), claimsPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClaimsPolicy{" +
            "id=" + id +
            ", shortName='" + shortName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", company=" + company +
            '}';
    }
}
