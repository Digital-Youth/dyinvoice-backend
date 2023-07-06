package com.dyinvoice.backend.model.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Invitation Form.")
public class InvitationForm {

    @ApiModelProperty(value = "AppUser ID.", required = true)
    private Long id;

    @ApiModelProperty(value = "Invitation email.", required = true)
    private String email;



}
