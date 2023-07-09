package com.dyinvoice.backend.model.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Product Form.")
public class ProductForm {

    @ApiModelProperty(value = "Product ID.", required = true)
    private long id;

    @ApiModelProperty(value = "Product name.")
    private String name;

    @ApiModelProperty(value = "Product description.")
    private String description;

    @ApiModelProperty(value = "Product description.")
    private double unitPrice;
}
