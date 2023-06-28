package com.dyinvoice.backend.service.implementation;

import com.dyinvoice.backend.dao.ClientDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
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

    private final ClientDAO clientDAO;

    private EntrepriseRepository entrepriseRepository;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO, EntrepriseRepository entrepriseRepository) {
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

        // Find associated entreprise
        Entreprise entreprise = entrepriseRepository.findById(form.getEntrepriseId())
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + form.getEntrepriseId()));

        // Convert form to entity
        Client client = FormToEntityConverter.convertClientFormToClient(form);

        // Set associated entreprise
        client.setEntreprise(entreprise);

        // Save client
        clientDAO.createClient(client);

        return client;

    }

}
