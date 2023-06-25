package com.dyinvoice.backend.model.view;


import lombok.Data;

@Data
public class ClientView {

    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String siret;

    private String address;

    private String postalCode;

    private String town;

    private String country;

}
