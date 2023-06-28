package com.dyinvoice.backend.dao;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;

import java.util.List;

public interface ClientDAO {

    Client createClient(Client client) throws ValidationException, ResourceNotFoundException;

    List<Client> getAllClients();

    Client getClientById(long id);

    Client updateClient(Client client);

    void deleteClient(long id);

    Client getClientByEmail(String email);

}
