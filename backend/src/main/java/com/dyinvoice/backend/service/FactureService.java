package com.dyinvoice.backend.service;

import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.model.form.FactureForm;

import java.util.List;
import java.util.Optional;

public interface FactureService {

    Facture createFacture(FactureForm form);

    List<Facture> getAllFactures();

    Facture getFactureById(long id);

    Facture updateFacture(Facture facture);

    Optional<Facture> findFactureByNumber(String number);

    void deleteFactureById(long id);

    Facture updateFactureStatus(long id, FactureStatus newStatus);
}
