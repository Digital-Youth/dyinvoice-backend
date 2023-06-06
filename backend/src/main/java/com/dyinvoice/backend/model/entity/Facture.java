package com.dyinvoice.backend.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String numFacture;

    private String label;

    private Date dateEmission;

    private Date reglement;

    private float tva;

    private float montant_ht;

    private float montant_tt;

    @OneToMany(mappedBy = "facture")
    private List<Client> clients;

    private Timestamp createdAt;

    private Timestamp updatedAt;


}
