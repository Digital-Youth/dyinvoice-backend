package com.dyinvoice.backend.model.form;


import com.dyinvoice.backend.model.entity.Entreprise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Register Form.")
public class RegisterForm {

    @ApiModelProperty(value = "AppUser ID.", required = true)
    private long id;

    @ApiModelProperty(value = "AppUser firstname.")
    private String firstName;

    @ApiModelProperty(value = "AppUser lastname.")
    private String lastName;

    @ApiModelProperty(value = "AppUser email.")
    private String email;

    @ApiModelProperty(value = "AppUser country.")
    private String country;

    @ApiModelProperty(value = "AppUser phoneNumber.")
    private String phoneNumber;

    @ApiModelProperty(value = "AppUser password.")
    private String password;

    // Entreprise fields
    @ApiModelProperty(value = "AppUser Company Name.")
    private String entrepriseName;


}
