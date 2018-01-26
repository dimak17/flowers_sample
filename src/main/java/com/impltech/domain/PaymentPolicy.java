package com.impltech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PaymentPolicy.
 */
@Entity
@Table(name = "payment_policy")
public class PaymentPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "paymentPolicy")
    @JsonIgnore
    private Set<ClientPaymentPolicy> clientPaymentPolicies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PaymentPolicy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public PaymentPolicy company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ClientPaymentPolicy> getClientPaymentPolicies() {
        return clientPaymentPolicies;
    }

    public PaymentPolicy clientPaymentPolicies(Set<ClientPaymentPolicy> clientPaymentPolicies) {
        this.clientPaymentPolicies = clientPaymentPolicies;
        return this;
    }

    public PaymentPolicy addClientPaymentPolicy(ClientPaymentPolicy clientPaymentPolicy) {
        this.clientPaymentPolicies.add(clientPaymentPolicy);
        clientPaymentPolicy.setPaymentPolicy(this);
        return this;
    }

    public PaymentPolicy removeClientPaymentPolicy(ClientPaymentPolicy clientPaymentPolicy) {
        this.clientPaymentPolicies.remove(clientPaymentPolicy);
        clientPaymentPolicy.setPaymentPolicy(null);
        return this;
    }

    public void setClientPaymentPolicies(Set<ClientPaymentPolicy> clientPaymentPolicies) {
        this.clientPaymentPolicies = clientPaymentPolicies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentPolicy paymentPolicy = (PaymentPolicy) o;
        if (paymentPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentPolicy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
