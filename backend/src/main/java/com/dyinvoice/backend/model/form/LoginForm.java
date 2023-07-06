package com.dyinvoice.backend.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Login Form.")

public class LoginForm {

    @ApiModelProperty(value = "AppUser email.")
    private String email;
    @ApiModelProperty(value = "AppUser password.")
    private String password;
}
