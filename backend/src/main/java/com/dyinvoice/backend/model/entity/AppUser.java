package com.dyinvoice.backend.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    @OneToOne
    private Entreprise entreprise;

    @ManyToOne
    private Role role;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
