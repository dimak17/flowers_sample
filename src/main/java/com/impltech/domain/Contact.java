package com.impltech.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "office_phone")
    private String officePhone;

    @Column(name = "email")
    private String email;

    @Column(name = "whatsapp")
    private String whatsapp;

    @Column(name = "skype")
    private String skype;

    @Column(name = "address")
    private String address;

    @Column(name = "fax")
    private String fax;

    @Column(name = "zipcode")
    private String zipcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Contact mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public Contact officePhone(String officePhone) {
        this.officePhone = officePhone;
        return this;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public Contact whatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getSkype() {
        return skype;
    }

    public Contact skype(String skype) {
        this.skype = skype;
        return this;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getAddress() {
        return address;
    }

    public Contact address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public Contact fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Contact zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if (contact.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contact.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", officePhone='" + getOfficePhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", skype='" + getSkype() + "'" +
            ", address='" + getAddress() + "'" +
            ", fax='" + getFax() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            "}";
    }
}
