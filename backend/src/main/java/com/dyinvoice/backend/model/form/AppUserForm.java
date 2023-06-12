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
@ApiModel(description = "AppUser Form.")
public class AppUserForm {

    @ApiModelProperty(value = "AppUser ID.", required = true)
    private long id;

    @ApiModelProperty(value = "AppUser firstname.")
    private String firstName;

    @ApiModelProperty(value = "AppUser lastname.")
    private String lastName;

    @ApiModelProperty(value = "AppUser email.")
    private String email;

    @ApiModelProperty(value = "AppUser phoneNumber.")
    private String phoneNumber;


}
