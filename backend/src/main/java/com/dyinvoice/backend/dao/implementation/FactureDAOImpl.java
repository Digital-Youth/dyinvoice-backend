package com.dyinvoice.backend.dao.implementation;


import com.dyinvoice.backend.config.EmailSender;
import com.dyinvoice.backend.config.Twilioconfig;
import com.dyinvoice.backend.dao.FactureDAO;
import com.dyinvoice.backend.exception.InvoiceApiException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.*;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.repository.ClientRepository;
import com.dyinvoice.backend.repository.FactureRepository;
import com.dyinvoice.backend.repository.PrestationRepository;
import com.dyinvoice.backend.repository.ProductRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class FactureDAOImpl implements FactureDAO {

    private final FactureRepository factureRepository;
    private final ProductRepository productRepository;
    private final PrestationRepository prestationRepository;
    private final Twilioconfig twilioConfig;
    private final EmailSender emailSender;
    private AppUserDAOImpl appUserDAO;
    private static final Logger logger = LoggerFactory.getLogger(FactureDAOImpl.class);
    private final ClientRepository clientRepository;

    //Generate Facture Number on this format INV-000001
    public synchronized String generateNumFacture() {
        Long lastId = factureRepository.findMaxId();
        String numFacture;
        if (lastId == null) {
            numFacture = String.format("INV-%06d", 1);
        } else {
            numFacture = String.format("INV-%06d", lastId + 1);
        }

        // Loop until a unique numFacture is found
        while(factureRepository.existsByNumFacture(numFacture)) {
            lastId++;
            numFacture = String.format("INV-%06d", lastId + 1);
        }

        return numFacture;
    }

    private void assignGeneratedNumFacture(Facture facture) {
        facture.setNumFacture(generateNumFacture());
    }


    @Override
    public Facture createFacture(Facture facture) throws ValidationException {
        assignGeneratedNumFacture(facture);
        validateAndSetProducts(facture);
        validateAndSetPrestations(facture);
        validateAndSetClients(facture);

        return factureRepository.save(facture);
    }


    private <T> Set<T> validateAndGetEntitiesFromDb(Set<T> entities, JpaRepository<T, Long> repository) throws ValidationException {
        Set<T> validEntities = new HashSet<>();
        for (T entity : entities) {
            repository.findById(((BaseEntity) entity).getId()).ifPresent(validEntities::add);
        }
        if (validEntities.size() != entities.size()) {
            String errorMsg = "Some entities were not found in the database.";
            logger.error(errorMsg);
            throw new ValidationException(errorMsg);
        }
        return validEntities;
    }

    private void validateAndSetProducts(Facture facture) throws ValidationException {
        facture.setProducts(validateAndGetEntitiesFromDb(facture.getProducts(), productRepository));
    }

    private void validateAndSetPrestations(Facture facture) throws ValidationException {
        facture.setPrestations(validateAndGetEntitiesFromDb(facture.getPrestations(), prestationRepository));
    }

    private void validateAndSetClients(Facture facture) throws ValidationException {
        facture.setClients(validateAndGetEntitiesFromDb(facture.getClients(), clientRepository));
    }


    public Client getClientByFacture(Facture facture) {
        return facture.getClients().stream().findFirst()
                .orElseThrow(() -> new InvoiceApiException("No client associated with the facture " + facture.getNumFacture()));
    }

    private String generateFactureLink(Facture facture) {
        return "https://your-frontend-domain.com/factures/" + facture.getNumFacture();
    }

    @Override
    public void sendFactureByEmail(Facture facture) throws Exception {
        Client client = getClientByFacture(facture);

        // Générer le lien unique pour la facture.
        String factureLink = generateFactureLink(facture);

        // Créer le contenu de l'e-mail.
        String content = "Cher client, veuillez cliquer sur le lien suivant pour visualiser votre facture : " + factureLink;

        // Envoyer l'e-mail.
        emailSender.sendEmail("yourEmail@example.com", client.getEmail(), "Votre Facture", content);
    }


    public void sendFactureByWhatsApp(Facture facture) throws Exception {
        Client client = getClientByFacture(facture);

        // Générer le lien unique pour la facture.
        String factureLink = generateFactureLink(facture);

        // Créer le contenu du message.
        String content = "Cher client, veuillez cliquer sur le lien suivant pour visualiser votre facture : " + factureLink;

        // Récupérer le numéro de téléphone de l'utilisateur actuellement connecté
        String senderNumber = appUserDAO.getLoggedInUserPhoneNumber();
        if (senderNumber == null) {
            throw new InvoiceApiException("Cannot retrieve phone number of the logged-in user.");
        }

        // Envoyer le message via WhatsApp.
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + client.getPhoneNumber()),
                new com.twilio.type.PhoneNumber("whatsapp:" + senderNumber),
                content
        ).create();

        // Vérification du statut du message si nécessaire
        if (message.getStatus() != Message.Status.DELIVERED && message.getStatus() != Message.Status.QUEUED) {
            throw new InvoiceApiException("Failed to send the invoice via WhatsApp.");
        }
    }




    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public Facture getFactureById(long id) {

        return factureRepository.findById(id).orElseThrow(() ->
                new InvoiceApiException("Facture with id " + id + " not found"));
    }

    @Override
    public Facture updateFacture(Facture facture) {
        return null;
    }

    @Override
    public Optional<Facture> findFactureByNumber(String number) {
        return factureRepository.findByNumFacture(number);
    }

    @Override
    public void deleteFactureById(long id) {
        factureRepository.deleteById(id);
    }

    @Override
    public Facture updateFactureStatus(long id, FactureStatus newStatus) {
        Facture facture = factureRepository.findById(id).orElseThrow(() ->
                new InvoiceApiException("Facture with id " + id + " not found"));
        facture.setStatus(newStatus);
        factureRepository.save(facture);
        return facture;
    }

}
