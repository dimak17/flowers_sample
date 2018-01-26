package com.impltech.validator.util;

import com.impltech.domain.Company;

/**
 * Created by alex
 */
public class CompanyValidator {

    public  static boolean checkCompanyLength(Company company) {

        return (company.getAddress().length() > 50 || company.getGeneralEmailAddress().length() > 50
            || company.getFarmSize().length() > 50 || company.getCity().length() > 50
            || company.getGeneralOfficePhone().length() > 24 || company.getLegalName().length() > 50
            || company.getFarmName().length() > 50);
    }
}
