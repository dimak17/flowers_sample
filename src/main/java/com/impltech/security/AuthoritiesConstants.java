package com.impltech.security;

/**
 * author platon
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String COMPANY_OWNER = "ROLE_COMPANY_OWNER";

    public static final String SALES_MANAGER = "ROLE_SALES_MANAGER";

    public static final String SALES_ASSISTANT = "ROLE_SALES_ASSISTANT";

    public static final String AGRONOM_ENGINEER = "ROLE_AGRONOM_ENGINEER";

    public static final String POST_HARVEST_MANAGER = "ROLE_POST_HARVEST_MANAGER";

    public static final String GENERAL_MANAGER = "ROLE_GENERAL_MANAGER";

    public static final String ACCOUNTANT_MANAGER = "ROLE_ACCOUNTANT_MANAGER";

    public static final String ACCOUNTANT_ASSISTANT = "ROLE_ACCOUNTANT_ASSISTANT";

    public static final String CLAIMS_MANAGER = "ROLE_CLAIMS_MANAGER";

    public static final String CLAIMS_ASSISTANT = "ROLE_CLAIMS_ASSISTANT";

    public static final String COORDINATION_MANAGER = "ROLE_COORDINATION_MANAGER";

    public static final String SYSTEM_MANAGER = "ROLE_SYSTEM_MANAGER";

    private AuthoritiesConstants() {
    }
}
