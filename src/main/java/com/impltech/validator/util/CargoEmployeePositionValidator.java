package com.impltech.validator.util;

import com.impltech.domain.CargoEmployeePosition;

public class CargoEmployeePositionValidator {
    public static boolean checkLengthValidation(CargoEmployeePosition cargoEmployeePosition) {
        if(cargoEmployeePosition.getName().length() > 25) {
            return true;
        }
        return false;
    }
}
