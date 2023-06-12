package com.dyinvoice.backend.utils;

import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.AppUserForm;


public class FormToEntityConverter {

    public static AppUser convertFormToAppUser(AppUserForm appUserForm) {

        AppUser appUser  = new AppUser();

        appUser.setId(appUserForm.getId());

        if(appUserForm.getFirstName() != null){
            appUser.setFirstName(appUserForm.getFirstName());
        }

        if(appUserForm.getLastName() != null){
            appUser.setLastName(appUserForm.getLastName());
        }

        if(appUserForm.getEmail() != null){
            appUser.setEmail(appUserForm.getEmail());
        }

        if(appUserForm.getPhoneNumber() != null){
            appUser.setPhoneNumber(appUserForm.getPhoneNumber());
        }


        return appUser;

    }
}
