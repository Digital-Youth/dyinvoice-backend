package com.dyinvoice.backend.dao.implementation;


import com.dyinvoice.backend.dao.PrestationDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.repository.PrestationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PrestationDAOImpl implements PrestationDAO {

    private static final Logger logger = LoggerFactory.getLogger(PrestationDAOImpl.class);

    private final PrestationRepository prestationRepository;

    private final EntrepriseRepository entrepriseRepository;


    @Autowired
    public PrestationDAOImpl(PrestationRepository prestationRepository, EntrepriseRepository entrepriseRepository) {
        this.prestationRepository = prestationRepository;
        this.entrepriseRepository = entrepriseRepository;
    }


    @Override
    public Prestation createPrestation(Prestation prestation) throws ValidationException, ResourceNotFoundException {

        logger.info("Check if the prestation exist");

        Optional<Prestation> existingPrestation = prestationRepository.findByName(prestation.getName());

        if(existingPrestation.isPresent()) {
            logger.error("Prestation already exists");

            throw new ValidationException("Prestation already exists");

        }
        logger.info("Checking if prestation has an associated entreprise...");
        if (prestation.getEntreprise() == null || prestation.getEntreprise().getId() == null) {
            logger.error("Prestation must have an associated entreprise.");
            throw new ValidationException("Prestation must have an associated entreprise");
        }

        // Fetch the entreprise from the database
        logger.info("Fetching entreprise from the database...");
        Entreprise checkEntreprise = entrepriseRepository.findById(prestation.getEntreprise().getId())

                .orElseThrow(() ->{
                            logger.error("Entreprise not found.");
                            return new ResourceNotFoundException("not found");
                        }
                );

        prestation.setEntreprise(checkEntreprise);

        logger.info("Saving prestation in the database...");

        return prestationRepository.save(prestation);

    }



    @Override
    public List<Prestation> getAllPrestations() {
        return prestationRepository.findAll();
    }

    @Override
    public Prestation updatePrestation(Prestation prestation) {
        return null;
    }

    @Override
    public void deletePrestation(long id) {
        prestationRepository.deleteById(id);
    }

    @Override
    public Prestation getPrestationByName(String name) {
        Optional<Prestation> prestation = prestationRepository.findByName(name);
        return prestation.orElse(null);

    }

    @Override
    public Prestation getPrestationById(long id) {
        Optional<Prestation> prestation = prestationRepository.findById(id);

        return prestation.orElse(null);
    }
}
