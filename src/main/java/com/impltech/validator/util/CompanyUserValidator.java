package com.impltech.validator.util;

import com.impltech.domain.CompanyUser;
import com.impltech.service.CompanyUserService;
import com.impltech.service.UserService;
import com.impltech.service.util.StaticContextAccessor;
import com.impltech.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * Created by platon
 */
public class CompanyUserValidator {

    public static boolean checkPasswordLength(String password) {
        return password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

    public static boolean checkPassword(String login, String password) {
        return StaticContextAccessor.getBean(UserService.class).getByLogin(login).filter(u -> StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(login))
            .map(u -> StaticContextAccessor.getBean(PasswordEncoder.class).matches(password, u.getPassword()))
            .orElse(false);
    }

    public static boolean checkLogin(String login) {
        return StringUtils.isNotBlank(login) && login.length() <= 50;
    }

    public static boolean checkForExistingLogin(CompanyUserService companyUserService, CompanyUser companyUser) {
        List<CompanyUser> currentCompanyUsers = companyUserService.findAllByCurrentCompanyId();
        for (CompanyUser currentCompanyUser : currentCompanyUsers) {
            if (currentCompanyUser.getAccountEmail().equalsIgnoreCase(companyUser.getAccountEmail().trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkLengthValidation(CompanyUser companyUser) {
        return (companyUser.getFullName().length() > 50 || companyUser.getAccountEmail().length() > 50
            || companyUser.getWorkEmail().length() > 50 || companyUser.getSkype().length() > 50
            || companyUser.getMobilePhone().length() > 50 || companyUser.getOfficePhone().length() > 50);
    }

}
