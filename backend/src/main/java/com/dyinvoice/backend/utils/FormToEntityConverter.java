package com.dyinvoice.backend.utils;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Invitations;
import com.dyinvoice.backend.model.form.*;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FormToEntityConverter {


    private static EntrepriseRepository entrepriseRepository;

    @Autowired
    public FormToEntityConverter(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

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
        appUser.setCountry(registerForm.getCountry());
        appUser.setPhoneNumber(registerForm.getPhoneNumber());
        appUser.setPassword(registerForm.getPassword());

        Entreprise entreprise = new Entreprise();
        entreprise.setName(registerForm.getEntrepriseName());

        appUser.setEntreprise(entreprise);

        return appUser;
    }

    public static Client convertClientFormToClient(ClientForm form) {
        Client client = new Client();

        client.setName(form.getName());
        client.setEmail(form.getEmail());
        client.setAddress(form.getAddress());
        client.setTown(form.getTown());

        return client;

    }

    public static AppUser convertLoginFormToAppUser(LoginForm loginForm) {
        AppUser appUser = new AppUser();

        appUser.setEmail(loginForm.getEmail());
        appUser.setPassword(loginForm.getPassword());

        return appUser;
    }

    public static Invitations convertInvitationFormToInvitation(InvitationForm invitationForm) {

        Invitations invitations = new Invitations();

        invitations.setEmail(invitationForm.getEmail());

        return invitations;
    }

    public static Invitations convertPasswordForm(PasswordForm passwordForm) {
        Invitations invitations = new Invitations();

        invitations.setEmail(passwordForm.getPassword());

        return invitations;
    }

    public static AppUser updateAppUserFromForm(AppUserForm form, AppUser existingAppUser) throws ResourceNotFoundException {

        if(form.getFirstName() != null) {
            existingAppUser.setFirstName(form.getFirstName());
        }

        if(form.getLastName()!= null) {
            existingAppUser.setLastName(form.getLastName());
        }

        if(form.getEmail() != null) {
            existingAppUser.setEmail(form.getEmail());
        }

        if(form.getPhoneNumber() != null) {
            existingAppUser.setPhoneNumber(form.getPhoneNumber());
        }

        if(form.getCountry()!= null) {
            existingAppUser.setCountry(form.getCountry());
        }

        if(form.getEntreprise() != null) {
            // Récupère l'instance existante de Entreprise
            Entreprise existingEntreprise = entrepriseRepository.findById(form.getEntreprise().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found with id : " + form.getEntreprise().getId()));

            // Met à jour l'entreprise avec les nouvelles informations du formulaire
            if(form.getEntreprise().getName()!= null) {
                existingEntreprise.setName(form.getEntreprise().getName());
            }

            if(form.getEntreprise().getCapitalSocial()!= null) {
                existingEntreprise.setCapitalSocial(form.getEntreprise().getCapitalSocial());
            }

            if(form.getEntreprise().getAddress()!= null) {
                existingEntreprise.setAddress(form.getEntreprise().getAddress());
            }

            if(form.getEntreprise().getSiret()!= null) {
                existingEntreprise.setSiret(form.getEntreprise().getSiret());
            }

            if(form.getEntreprise().getFormeJuridique()!= null) {
                existingEntreprise.setFormeJuridique(form.getEntreprise().getFormeJuridique());
            }

            // Sauvegarde les modifications de l'entreprise
            entrepriseRepository.save(existingEntreprise);

            existingAppUser.setEntreprise(existingEntreprise);
        }

        // ...

        return existingAppUser;
    }

}
