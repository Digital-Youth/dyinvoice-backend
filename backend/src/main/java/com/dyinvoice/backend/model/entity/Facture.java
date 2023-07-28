package com.dyinvoice.backend.model.entity;


import com.dyinvoice.backend.model.enumaration.FactureStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private FactureStatus status;

    @ManyToMany
    @JoinTable(
            name = "facture_product",
            joinColumns = @JoinColumn(name = "facture_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @ManyToMany
    @JoinTable(
            name = "facture_prestations",
            joinColumns = @JoinColumn(name = "facture_id"),
            inverseJoinColumns = @JoinColumn(name = "prestation_id")
    )
    private Set<Prestation> prestations;

    private Timestamp createdAt;

    private Timestamp updatedAt;


}
