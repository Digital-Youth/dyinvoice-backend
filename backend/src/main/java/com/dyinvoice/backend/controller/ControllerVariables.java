package com.dyinvoice.backend.controller;

public interface ControllerVariables {

    String[] devAntPatterns = new String[]{
            "/swagger/**",
            "/h2-console/**"
    };

    String[] userAntPatterns = new String[]{
            "/v1/user/**"
    };

    String[] staffAntPatterns = new String[]{
            "/admin/**"
    };

    String ADMIN_ROLE_NAME = "admin";
    String USER_ROLE_NAME = "user";
    String STAFF_ROLE_NAME = "staff";
    String ROLE_PREFIX = "ROLE_";
}
