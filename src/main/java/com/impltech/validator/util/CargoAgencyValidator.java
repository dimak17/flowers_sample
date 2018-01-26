package com.impltech.validator.util;

import com.impltech.domain.CargoAgency;

/**
 * @author dima
 */
public class CargoAgencyValidator {
    public static boolean checkLengthValidation(CargoAgency cargoAgency) {
        if(cargoAgency.getName().length() > 50 || cargoAgency.getMainAddress().length() > 50 || cargoAgency.getWebPage().length() > 50 || cargoAgency.getAdditionalAddress().length() > 50 || cargoAgency.getOfficePhone().length() > 50 || cargoAgency.getEmail().length() > 50) {
            return true;
        }
        return false;
    }
}
