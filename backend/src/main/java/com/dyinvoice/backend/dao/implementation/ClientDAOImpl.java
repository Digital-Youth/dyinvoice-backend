package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.dao.ClientDAO;
import com.dyinvoice.backend.exception.InvoiceApiException;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.repository.AppUserRepository;
import com.dyinvoice.backend.repository.ClientRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientDAOImpl implements ClientDAO {

    private final ClientRepository clientRepository;
    private final EntrepriseRepository entrepriseRepository;

    @Autowired
    public ClientDAOImpl(ClientRepository clientRepository, EntrepriseRepository entrepriseRepository) {
        this.clientRepository = clientRepository;
        this.entrepriseRepository = entrepriseRepository;
    }
    @Override
    public Client createClient(Client client) throws ValidationException, ResourceNotFoundException {
        if (client.getEntreprise() == null || client.getEntreprise().getId() == null) {
            throw new ValidationException("Client must have an associated entreprise");
        }

        Entreprise checkEntreprise = entrepriseRepository.findById(client.getEntreprise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("not found"));

        client.setEntreprise(checkEntreprise);
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
    public Client updateClient(Client client) {
        return null;
    }

    @Override
    public void deleteClient(long id) {

        clientRepository.deleteById(id);
    }
}
