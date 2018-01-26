package com.impltech.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author platon
 */
public class CompanyUserDTO implements Serializable {

    private Long id;

    private String skype;

    private String patronymicName;

    private String secondaryEmail;

    private String mobilePhone;

    private String secondPhone;

    private String whatsUp;

    private String companyName;

    private Long userId;

    private String userLogin;

    private Long companyId;

    private String companyFarmName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }
    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }
    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getSecondPhone() {
        return secondPhone;
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone;
    }
    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyFarmName() {
        return companyFarmName;
    }

    public void setCompanyFarmName(String companyFarmName) {
        this.companyFarmName = companyFarmName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyUserDTO companyUserDTO = (CompanyUserDTO) o;

        if ( ! Objects.equals(id, companyUserDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyUserDTO{" +
            "id=" + id +
            ", skype='" + skype + "'" +
            ", patronymicName='" + patronymicName + "'" +
            ", secondaryEmail='" + secondaryEmail + "'" +
            ", mobilePhone='" + mobilePhone + "'" +
            ", secondPhone='" + secondPhone + "'" +
            ", whatsUp='" + whatsUp + "'" +
            ", companyName='" + companyName + "'" +
            '}';
    }
}
