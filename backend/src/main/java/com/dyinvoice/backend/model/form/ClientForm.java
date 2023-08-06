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
@ApiModel(description = "Client Form.")
public class ClientForm {

    @ApiModelProperty(value = "Client ID.", required = true)
    private Long id;

    @ApiModelProperty(value = "Client name.")
    private String name;

    @ApiModelProperty(value = "Client email.")
    private String email;

    @ApiModelProperty(value = "Client address.")
    private String address;

    @ApiModelProperty(value = "Client Town.")
    private String town;

}
