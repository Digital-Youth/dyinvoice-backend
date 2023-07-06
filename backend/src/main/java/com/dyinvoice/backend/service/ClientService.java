package com.dyinvoice.backend.service;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.form.ClientForm;

import java.util.List;

public interface ClientService {

    Client createClient(ClientForm form) throws ValidationException, ResourceNotFoundException;

    List<Client> getAllClients();
    Client getClientById(long id) throws ResourceNotFoundException;

    Client getClientByEmail(String email) throws ResourceNotFoundException;
    Client updateClient(Client client) throws ValidationException, ResourceNotFoundException;
    void deleteClient(long id);
}
