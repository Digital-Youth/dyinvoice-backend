package com.dyinvoice.backend.service;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.model.form.ClientForm;
import com.dyinvoice.backend.model.form.PrestationForm;

import java.util.List;

public interface PrestationService {

    Prestation createPrestation(PrestationForm form) throws ValidationException, ResourceNotFoundException;

    List<Prestation> getAllPrestation();

    Prestation getPrestationByName(String name) throws ResourceNotFoundException;
    Prestation updatePrestation(Prestation prestation) throws ValidationException, ResourceNotFoundException;
    void deletePrestation(long id);

    Prestation getPrestationById(long id) throws ResourceNotFoundException;
}
