package com.dyinvoice.backend.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String siret_siren;

    private String address;

    private String postalCode;

    private String town;

    private String country;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

}
