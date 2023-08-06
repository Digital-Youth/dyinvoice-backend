package com.dyinvoice.backend.service.implementation;


import com.dyinvoice.backend.dao.FactureDAO;
import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.model.form.FactureForm;
import com.dyinvoice.backend.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FactureServiceImpl implements FactureService {

    private final FactureDAO factureDAO;

    @Autowired
    public FactureServiceImpl(FactureDAO factureDAO) {
        this.factureDAO = factureDAO;
    }

    @Override
    public Facture createFacture(FactureForm form) {
        return null;
    }

    @Override
    public List<Facture> getAllFactures() {
        return null;
    }

    @Override
    public Facture getFactureById(long id) {
        return null;
    }

    @Override
    public Facture updateFacture(Facture facture) {
        return null;
    }

    @Override
    public Optional<Facture> findFactureByNumber(String number) {
        return Optional.empty();
    }

    @Override
    public void deleteFactureById(long id) {

    }

    @Override
    public Facture updateFactureStatus(long id, FactureStatus newStatus) {
        return null;
    }
}
