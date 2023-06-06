package com.dyinvoice.backend.model.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    private String shortName;
}
