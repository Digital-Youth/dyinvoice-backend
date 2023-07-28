package com.dyinvoice.backend.model.form;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Facture Form.")
public class FactureForm {

    private String numFacture;
    private String label;
    private Date dateEmission;
    private Date reglement;
    private float tva;
    private float montant_ht;
    private float montant_tt;
    private long clientId;

}
