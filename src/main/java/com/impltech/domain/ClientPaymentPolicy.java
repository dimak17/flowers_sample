package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A ClientPaymentPolicy.
 */
@Entity
@Table(name = "client_payment_policy")
public class ClientPaymentPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terms_in_days")
    private Long termsInDays;

    @Column(name = "terms_in_sum", precision=10, scale=2)
    private BigDecimal termsInSum;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    private Client client;

    @ManyToOne
    private PaymentPolicy paymentPolicy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTermsInDays() {
        return termsInDays;
    }

    public ClientPaymentPolicy termsInDays(Long termsInDays) {
        this.termsInDays = termsInDays;
        return this;
    }

    public void setTermsInDays(Long termsInDays) {
        this.termsInDays = termsInDays;
    }

    public BigDecimal getTermsInSum() {
        return termsInSum;
    }

    public ClientPaymentPolicy termsInSum(BigDecimal termsInSum) {
        this.termsInSum = termsInSum;
        return this;
    }

    public void setTermsInSum(BigDecimal termsInSum) {
        this.termsInSum = termsInSum;
    }

    public Boolean isStatus() {
        return status;
    }

    public ClientPaymentPolicy status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public ClientPaymentPolicy client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentPolicy getPaymentPolicy() {
        return paymentPolicy;
    }

    public ClientPaymentPolicy paymentPolicy(PaymentPolicy paymentPolicy) {
        this.paymentPolicy = paymentPolicy;
        return this;
    }

    public void setPaymentPolicy(PaymentPolicy paymentPolicy) {
        this.paymentPolicy = paymentPolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientPaymentPolicy clientPaymentPolicy = (ClientPaymentPolicy) o;
        if (clientPaymentPolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientPaymentPolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientPaymentPolicy{" +
            "id=" + getId() +
            ", termsInDays='" + getTermsInDays() + "'" +
            ", termsInSum='" + getTermsInSum() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
