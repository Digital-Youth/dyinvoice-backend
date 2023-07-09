package com.dyinvoice.backend.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Password Form.")
public class PasswordForm {

    @ApiModelProperty(value = "Invitation ID.", required = true)
    private Long invitationId;

    @ApiModelProperty(value = "User password.", required = true)
    private String password;
}
