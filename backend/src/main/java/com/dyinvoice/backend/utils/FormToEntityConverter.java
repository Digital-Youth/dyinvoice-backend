package com.dyinvoice.backend.utils;

import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.RegisterForm;


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

    public static AppUser convertRegisterFormToAppUser(RegisterForm registerForm) {
        AppUser appUser = new AppUser();

        // Assuming that RegisterForm has these methods...
        appUser.setFirstName(registerForm.getFirstName());
        appUser.setLastName(registerForm.getLastName());
        appUser.setEmail(registerForm.getEmail());
        appUser.setPhoneNumber(registerForm.getPhoneNumber());
        appUser.setPassword(registerForm.getPassword());

        Entreprise entreprise = new Entreprise();
        entreprise.setName(registerForm.getEntrepriseName());
        entreprise.setSiret(registerForm.getSiret());

        appUser.setEntreprise(entreprise);

        return appUser;
    }
}
