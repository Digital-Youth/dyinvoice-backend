package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.dao.ClientDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.repository.ClientRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Component
public class ClientDAOImpl implements ClientDAO {

    private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);

    private final ClientRepository clientRepository;
    private final EntrepriseRepository entrepriseRepository;

    @Autowired
    public ClientDAOImpl(ClientRepository clientRepository, EntrepriseRepository entrepriseRepository) {
        this.clientRepository = clientRepository;
        this.entrepriseRepository = entrepriseRepository;
    }
    @Override
    public Client createClient(Client client) throws ValidationException, ResourceNotFoundException {

        //Check if client exist
        logger.info("Checking if client already exists...");
        Optional<Client> existClient = clientRepository.findByEmail(client.getEmail());
        if (existClient.isPresent()) {
            logger.error("Client already exists.");
            throw new ValidationException("Client already exists");
        }
        logger.info("Saving client in the database...");
        return clientRepository.save(client);

    }


    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(long id) {

        Optional<Client> client = clientRepository.findById(id);

        return client.orElse(null);
    }

    @Override
    public Client getClientByEmail(String email) {

        Optional<Client> client = clientRepository.findByEmail(email);

        return client.orElse(null);
    }

    @Override
    public Client updateClient(Client client) {
        return null;
    }

    @Override
    public void deleteClient(long id) {

        clientRepository.deleteById(id);
    }


}
