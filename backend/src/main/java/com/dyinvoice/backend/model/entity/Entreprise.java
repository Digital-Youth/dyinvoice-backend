package com.dyinvoice.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private AppUser appUser;

    private Timestamp createdAt;

    private Timestamp updatedAt;




}
