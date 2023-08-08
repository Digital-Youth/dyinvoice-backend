package com.dyinvoice.backend.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Prestation implements BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int honorary;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @Override
    public Long getId() {
        return id;
    }
}
