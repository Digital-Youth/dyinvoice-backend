package com.dyinvoice.backend.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String capitalSocial;

    private String address;

    private String siret;

    private String formeJuridique;

    private Timestamp createdAt;

    private Timestamp updatedAt;




}
