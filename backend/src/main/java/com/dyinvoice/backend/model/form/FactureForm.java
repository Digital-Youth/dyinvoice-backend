package com.dyinvoice.backend.model.form;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Facture Form.")
public class FactureForm {

    private Long id;
    private String numFacture;
    private String label;
    private Date dateEmission;
    private Date reglement;
    private float tva;
    private float montant_ht;
    private Set<Long> productIds = new HashSet<>();
    private Set<Long> prestationIds = new HashSet<>();
    private Set<Long> clientIds;

}
