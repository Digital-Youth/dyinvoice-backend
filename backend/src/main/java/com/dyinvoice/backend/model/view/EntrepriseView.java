package com.dyinvoice.backend.model.view;

import com.dyinvoice.backend.model.entity.AppUser;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class EntrepriseView {

    private long id;

    private String name;

    private String capitalSocial;

    private String address;

    private String siret;

    private String formeJuridique;

    private AppUser appUser;

}
