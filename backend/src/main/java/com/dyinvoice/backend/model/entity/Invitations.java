package com.dyinvoice.backend.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Invitations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private LocalDateTime expiryDate;

    private String role;

    private String token;

    private boolean emailSent = false;

    private String senderEmail;

    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

}
