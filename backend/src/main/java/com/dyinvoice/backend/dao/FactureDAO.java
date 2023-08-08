package com.dyinvoice.backend.dao;

import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.enumaration.FactureStatus;

import java.util.List;
import java.util.Optional;

public interface FactureDAO {

    Facture createFacture(Facture facture) throws ValidationException;

    List<Facture> getAllFactures();

    Facture getFactureById(long id);

    Facture updateFacture(Facture facture);

    Optional<Facture> findFactureByNumber(String number);

    void deleteFactureById(long id);

    Facture updateFactureStatus(long id, FactureStatus newStatus);

    void sendFactureByEmail(Facture facture) throws Exception;
    void sendFactureByWhatsApp(Facture facture) throws Exception;



}
