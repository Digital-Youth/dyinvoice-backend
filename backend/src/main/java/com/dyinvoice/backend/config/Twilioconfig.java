package com.dyinvoice.backend.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Twilioconfig {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    public String getAccountSid() {
        return ACCOUNT_SID;
    }

    public String getAuthToken() {
        return AUTH_TOKEN;
    }
}
