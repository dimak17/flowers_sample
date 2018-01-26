package com.impltech.validator.util;

import com.impltech.domain.CargoEmployee;

/**
 * @author dima
 */
public class CargoEmployeeValidator {
    public static boolean checkLengthValidation(CargoEmployee cargoEmployee) {
        if(cargoEmployee.getFullName().length() > 50 || cargoEmployee.getEmail().length() > 50 || cargoEmployee.getSkype().length() > 50) {
            return true;
        }
        return false;
    }
}
