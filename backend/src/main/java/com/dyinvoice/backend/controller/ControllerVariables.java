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
}
