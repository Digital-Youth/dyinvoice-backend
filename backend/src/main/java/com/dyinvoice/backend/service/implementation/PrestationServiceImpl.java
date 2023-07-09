package com.dyinvoice.backend.service.implementation;

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

    @Autowired
    public PrestationServiceImpl(PrestationDAO prestationDAO, EntrepriseRepository entrepriseRepository) {
        this.prestationDAO = prestationDAO;
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Prestation createPrestation(PrestationForm form) throws ValidationException, ResourceNotFoundException {
        List<String> errorList = FormValidator.validatePrestationForm(form);
        if (errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        Entreprise entreprise = entrepriseRepository.findById(form.getEntrepriseId())
                .orElseThrow(() -> new ResourceNotFoundException("Entreprise not found with id " + form.getEntrepriseId()));

        Prestation prestation = FormToEntityConverter.convertPrestationFormToPrestation(form);

        prestation.setEntreprise(entreprise);

        prestationDAO.createPrestation(prestation);

        return prestation;
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
}
