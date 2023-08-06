package com.dyinvoice.backend.service.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.dao.ClientDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.form.ClientForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.service.ClientService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ClientServiceImpl implements ClientService {

    private final AppUserDAO appUserDAO;
    private final ClientDAO clientDAO;

    private EntrepriseRepository entrepriseRepository;

    @Autowired
    public ClientServiceImpl(AppUserDAO appUserDAO, ClientDAO clientDAO, EntrepriseRepository entrepriseRepository) {
        this.appUserDAO = appUserDAO;
        this.clientDAO = clientDAO;
        this.entrepriseRepository = entrepriseRepository;
    }


    @Override
    public Client createClient(ClientForm form) throws ValidationException, ResourceNotFoundException {

        // Validate form
        List<String> errorList = FormValidator.validateClientForm(form);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        Long entrepriseId = appUserDAO.getLoggedInUserEntrepriseId();
        if (entrepriseId == null) {
            throw new ResourceNotFoundException("Entreprise not found for the logged-in user");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + entrepriseId));


        // Convert form to entity
        Client client = FormToEntityConverter.convertClientFormToClient(form);

        // Set associated entreprise
        client.setEntreprise(entreprise);

        // Save client
        clientDAO.createClient(client);

        return client;

    }

    @Override
    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    @Override
    public Client getClientById(long id) throws ResourceNotFoundException {
        Client client = clientDAO.getClientById(id);
        if (client == null) {
            throw new ResourceNotFoundException("Client not found with id " + id);
        }
        return client;
    }

    @Override
    public Client getClientByEmail(String email) throws ResourceNotFoundException {

        Client client = clientDAO.getClientByEmail(email);

        if(client == null) {
            throw new ResourceNotFoundException("Client not found with email " + email);
        }
        return client;
    }

    @Override
    public Client updateClient(Client client) throws ValidationException, ResourceNotFoundException {
        Client updatedClient = clientDAO.updateClient(client);
        if (updatedClient == null) {
            throw new ValidationException("Client update failed.");
        }
        return updatedClient;
    }

    @Override
    public void deleteClient(long id) {
        clientDAO.deleteClient(id);
    }

}
