package com.dyinvoice.backend.service.implementation;


import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.dao.FactureDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.model.form.FactureForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.service.FactureService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureServiceImpl implements FactureService {

    private final FactureDAO factureDAO;
    private final AppUserDAO appUserDAO;
    private final EntrepriseRepository entrepriseRepository;

    private final FormToEntityConverter converter;
    @Autowired
    public FactureServiceImpl(FactureDAO factureDAO, AppUserDAO appUserDAO, EntrepriseRepository entrepriseRepository, FormToEntityConverter converter) {
        this.factureDAO = factureDAO;
        this.appUserDAO = appUserDAO;
        this.entrepriseRepository = entrepriseRepository;
        this.converter = converter;
    }

    @Override
    public Facture createFacture(FactureForm form) throws ResourceNotFoundException, ValidationException {

        Long entrepriseId = appUserDAO.getLoggedInUserEntrepriseId();
        if (entrepriseId == null) {
            throw new ResourceNotFoundException("Entreprise not found for the logged-in user");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + entrepriseId));

        // Convert form to entity
        Facture facture = converter.convertFactureFormToFacture(form);
        facture.setEntreprise(entreprise);

        factureDAO.createFacture(facture);

        return facture;
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureDAO.getAllFactures();
    }

    @Override
    public Facture getFactureById(long id) {
        return factureDAO.getFactureById(id);
    }

    @Override
    public Facture updateFacture(Facture facture) {
        return factureDAO.updateFacture(facture);
    }

    @Override
    public Optional<Facture> findFactureByNumber(String number) {
        return factureDAO.findFactureByNumber(number);
    }

    @Override
    public void deleteFactureById(long id) {
        factureDAO.deleteFactureById(id);
    }

    @Override
    public Facture updateFactureStatus(long id, FactureStatus newStatus) {
        return factureDAO.updateFactureStatus(id, newStatus);
    }

    @Override
    public void sendFactureByEmail(Facture facture) throws Exception {
        factureDAO.sendFactureByEmail(facture);
    }

    @Override
    public void sendFactureByWhatsApp(Facture facture) throws Exception {
        factureDAO.sendFactureByWhatsApp(facture);
    }
}
