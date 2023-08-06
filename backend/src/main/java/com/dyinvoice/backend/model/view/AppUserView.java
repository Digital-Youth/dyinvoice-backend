package com.dyinvoice.backend.model.view;

import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class AppUserView {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String phoneNumber;

    private String entreprise;

    private Set<Role> roles;


}
