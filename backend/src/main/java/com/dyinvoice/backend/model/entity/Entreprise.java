package com.dyinvoice.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entreprise")
    @ToString.Exclude
    private AppUser appUser;

    private Timestamp createdAt;

    private Timestamp updatedAt;




}
