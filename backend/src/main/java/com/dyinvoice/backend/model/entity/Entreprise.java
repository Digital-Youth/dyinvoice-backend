package com.dyinvoice.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String capitalSocial;

    private String address;

    private String siret;

    private String formeJuridique;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entreprise")
    private AppUser appUser;

    private Timestamp createdAt;

    private Timestamp updatedAt;




}
