package com.dyinvoice.backend.dao;



import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Prestation;

import java.util.List;

public interface PrestationDAO {

    Prestation createPrestation(Prestation prestation) throws ValidationException, ResourceNotFoundException;

    List<Prestation> getAllPrestations();

    Prestation updatePrestation(Prestation prestation);

    void deletePrestation(long id);

    Prestation getPrestationByName(String name);

    Prestation getPrestationById(long id);
}
