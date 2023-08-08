package com.dyinvoice.backend.utils;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.*;
import com.dyinvoice.backend.model.form.*;
import com.dyinvoice.backend.repository.ClientRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.repository.PrestationRepository;
import com.dyinvoice.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class FormToEntityConverter {


    private static EntrepriseRepository entrepriseRepository;
    private final ProductRepository productRepository;
    private final PrestationRepository prestationRepository;

    private final ClientRepository clientRepository;
    @Autowired
    public FormToEntityConverter(EntrepriseRepository entrepriseRepository, ProductRepository productRepository, PrestationRepository prestationRepository, ClientRepository clientRepository) {
        this.entrepriseRepository = entrepriseRepository;
        this.productRepository = productRepository;
        this.prestationRepository = prestationRepository;
        this.clientRepository = clientRepository;
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

    public Facture convertFactureFormToFacture(FactureForm form) throws ResourceNotFoundException {
        Facture facture = new Facture();

        facture.setId(form.getId());
        facture.setNumFacture(form.getNumFacture());
        facture.setLabel(form.getLabel());
        facture.setDateEmission(form.getDateEmission());
        facture.setReglement(form.getReglement());
        facture.setTva(form.getTva());
        facture.setMontant_ht(form.getMontant_ht());

        // Converting IDs to actual entities
        facture.setProducts(getProductsFromIds(form.getProductIds()));
        facture.setPrestations(getPrestationsFromIds(form.getPrestationIds()));
        facture.setClients(getClientsFromIds(form.getClientIds()));

        return facture;
    }

    private Set<Product> getProductsFromIds(Set<Long> productIds) throws ResourceNotFoundException {
        if (productIds == null || productIds.isEmpty()) {
            return new HashSet<>(); // Retourner un Set vide si aucun produit n'est fourni.
        }

        return productIds.stream()
                .map(productId -> {
                    try {
                        return productRepository.findById(productId)
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Set<Prestation> getPrestationsFromIds(Set<Long> prestationIds) throws ResourceNotFoundException {
        if (prestationIds == null || prestationIds.isEmpty()) {
            return new HashSet<>(); // Retourner un Set vide si aucune prestation n'est fournie.
        }

        return prestationIds.stream()
                .map(prestationId -> {
                    try {
                        return prestationRepository.findById(prestationId)
                                .orElseThrow(() -> new ResourceNotFoundException("Prestation not found with id " + prestationId));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    private Set<Client> getClientsFromIds(Set<Long> clientIds) throws ResourceNotFoundException {
        return clientIds.stream()
                .map(clientId -> {
                    try {
                        return clientRepository.findById(clientId)
                                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id " + clientId));
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
    }

    public static  Product convertProductFormToProduct(ProductForm form) {
        Product product = new Product();

        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setUnitPrice(form.getUnitPrice());

        return product;
    }

    public static Prestation convertPrestationFormToPrestation(PrestationForm form) {

        Prestation prestation = new Prestation();

        prestation.setName(form.getName());
        prestation.setDescription(form.getDescription());

        return prestation;
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
