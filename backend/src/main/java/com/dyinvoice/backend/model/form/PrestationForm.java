package com.dyinvoice.backend.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Prestation Form.")
public class PrestationForm {

    @ApiModelProperty(value = "Prestation ID.", required = true)
    private long id;

    @ApiModelProperty(value = "Prestation name.")
    private String name;

    @ApiModelProperty(value = "Prestation description.")
    private String description;


}
