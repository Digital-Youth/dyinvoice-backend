package com.dyinvoice.backend.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Prestation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;
}
