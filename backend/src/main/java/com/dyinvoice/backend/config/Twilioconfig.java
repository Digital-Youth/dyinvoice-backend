package com.dyinvoice.backend.config;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
