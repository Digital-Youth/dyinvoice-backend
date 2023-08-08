package com.dyinvoice.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String capitalSocial;

    private String address;

    private String siret;

    private String formeJuridique;

    @JsonBackReference
    @OneToMany(mappedBy = "entreprise")
    @ToString.Exclude
    private List<Facture> factures;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private AppUser appUser;

    private Timestamp createdAt;

    private Timestamp updatedAt;


    @PrePersist
    public void onPrePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }


}
