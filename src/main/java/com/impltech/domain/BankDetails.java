package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BankDetails.
 */
@Entity
@Table(name = "bank_details")
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "general")
    private String general;

    @Column(name = "alternative")
    private String alternative;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGeneral() {
        return general;
    }

    public BankDetails general(String general) {
        this.general = general;
        return this;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getAlternative() {
        return alternative;
    }

    public BankDetails alternative(String alternative) {
        this.alternative = alternative;
        return this;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public Company getCompany() {
        return company;
    }

    public BankDetails company(Company company) {
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
        BankDetails bankDetails = (BankDetails) o;
        if (bankDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankDetails{" +
            "id=" + getId() +
            ", general='" + getGeneral() + "'" +
            ", alternative='" + getAlternative() + "'" +
            "}";
    }
}
