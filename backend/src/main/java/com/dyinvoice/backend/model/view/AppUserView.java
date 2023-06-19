package com.dyinvoice.backend.model.view;

import com.dyinvoice.backend.model.entity.Entreprise;
import lombok.Data;

@Data
public class AppUserView {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;


}
