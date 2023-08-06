package com.dyinvoice.backend.service.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.dao.PrestationDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.model.form.PrestationForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.service.PrestationService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrestationServiceImpl implements PrestationService {

    private final PrestationDAO prestationDAO;
    private final EntrepriseRepository entrepriseRepository;
    private final AppUserDAO appUserDAO;

    @Autowired
    public PrestationServiceImpl(PrestationDAO prestationDAO, EntrepriseRepository entrepriseRepository, AppUserDAO appUserDAO) {
        this.prestationDAO = prestationDAO;
        this.entrepriseRepository = entrepriseRepository;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public Prestation createPrestation(PrestationForm form) throws ValidationException, ResourceNotFoundException {
        List<String> errorList = FormValidator.validatePrestationForm(form);
        if (errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        Long entrepriseId = appUserDAO.getLoggedInUserEntrepriseId();
        if (entrepriseId == null) {
            throw new ResourceNotFoundException("Entreprise not found for the logged-in user");
        }

        Entreprise entreprise = entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + entrepriseId));

        Prestation prestation = FormToEntityConverter.convertPrestationFormToPrestation(form);
        prestation.setEntreprise(entreprise);

        return prestationDAO.createPrestation(prestation);
    }

    @Override
    public List<Prestation> getAllPrestation() {
        return prestationDAO.getAllPrestations();
    }

    @Override
    public Prestation getPrestationByName(String name) throws ResourceNotFoundException {

        Prestation prestation = prestationDAO.getPrestationByName(name);

        if(prestation == null) {
            throw new ResourceNotFoundException("Prestation not found with name " + prestation);
        }
        return prestation;
    }

    @Override
    public Prestation updatePrestation(Prestation prestation) throws ValidationException, ResourceNotFoundException {
        Prestation updatePrestation = prestationDAO.updatePrestation(prestation);
        if(updatePrestation == null) {
            throw new ValidationException("Prestation update failed");
        }

        return updatePrestation;
    }

    @Override
    public void deletePrestation(long id) {

        prestationDAO.deletePrestation(id);

    }

    @Override
    public Prestation getPrestationById(long id) throws ResourceNotFoundException {
        Prestation prestation = prestationDAO.getPrestationById(id);
        if (prestation == null) {
            throw new ResourceNotFoundException("Prestation not found with id " + id);
        }
        return prestation;
    }
}
