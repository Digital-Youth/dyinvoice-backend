package com.dyinvoice.backend.model.entity;


import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String note;

    @JsonManagedReference("facture-client")
    @OneToMany(mappedBy = "facture")
    private Set<Client> clients;

    @Enumerated(EnumType.STRING)
    private FactureStatus status;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

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


    public void setMontant_ht(float montant_ht) {
        this.montant_ht = montant_ht;
        computeTotal();
    }

    public void setTva(float tva) {
        this.tva = tva;
        computeTotal();
    }

    private void computeTotal() {
        this.montant_tt = this.montant_ht + this.tva;
    }


    @PrePersist
    public void onPrePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
