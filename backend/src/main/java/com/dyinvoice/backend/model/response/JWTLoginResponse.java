package com.dyinvoice.backend.model.response;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTLoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
}
