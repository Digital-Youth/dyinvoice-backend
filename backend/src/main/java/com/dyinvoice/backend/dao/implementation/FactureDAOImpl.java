package com.dyinvoice.backend.dao.implementation;


import com.dyinvoice.backend.dao.FactureDAO;
import com.dyinvoice.backend.exception.InvoiceApiException;
import com.dyinvoice.backend.model.entity.Facture;
import com.dyinvoice.backend.model.entity.Prestation;
import com.dyinvoice.backend.model.entity.Product;
import com.dyinvoice.backend.model.enumaration.FactureStatus;
import com.dyinvoice.backend.repository.FactureRepository;
import com.dyinvoice.backend.repository.PrestationRepository;
import com.dyinvoice.backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class FactureDAOImpl implements FactureDAO {

    private final FactureRepository factureRepository;
    private final ProductRepository productRepository;
    private final PrestationRepository prestationRepository;

    private static final Logger logger = LoggerFactory.getLogger(FactureDAOImpl.class);

    //Generate Facture Number on this format INV-000001
    public String generateNumFacture() {
        Long lastId = factureRepository.findMaxId();
        String numFacture;
        if (lastId == null) {
            numFacture = String.format("INV-%06d", 1);
        } else {
            numFacture = String.format("INV-%06d", lastId + 1);
        }

        // Loop until a unique numFacture is found
        while(factureRepository.existsByNumFacture(numFacture)) {
            lastId++;
            numFacture = String.format("INV-%06d", lastId + 1);
        }

        return numFacture;
    }
    @Override
    public Facture createFacture(Facture facture) {

        // Generate numFacture
        String numFacture = generateNumFacture();
        facture.setNumFacture(numFacture);

        Set<Product> products = new HashSet<>();
        for (Product product : facture.getProducts()) {
            Optional<Product> productOptional = productRepository.findById(product.getId());
            productOptional.ifPresent(products::add);
        }
        facture.setProducts(products);

        Set<Prestation> prestations = new HashSet<>();
        for(Prestation prestation : facture.getPrestations()) {
            Optional<Prestation> prestationOptional = prestationRepository.findById(prestation.getId());
            prestationOptional.ifPresent(prestations::add);
        }
        facture.setPrestations(prestations);

        return factureRepository.save(facture);
    }

    @Override
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    @Override
    public Facture getFactureById(long id) {

        Optional<Facture> factureOptional = factureRepository.findById(id);
        if(factureOptional.isPresent()) {
            throw new InvoiceApiException("Facture with this " + id + " already exist");
        }
        return factureOptional.orElse(null);
    }

    @Override
    public Facture updateFacture(Facture facture) {
        return null;
    }

    @Override
    public Optional<Facture> findFactureByNumber(String number) {
        Optional<Facture> checkFacture = factureRepository.findByNumFacture(number);
        if(checkFacture.isPresent()){
            throw new InvoiceApiException("Facture with number" + checkFacture.get().getNumFacture());
        }
        return factureRepository.findByNumFacture(number);
    }

    @Override
    public void deleteFactureById(long id) {

        factureRepository.deleteById(id);
    }

    @Override
    public Facture updateFactureStatus(long id, FactureStatus newStatus) {
        Optional<Facture> factureOptional = factureRepository.findById(id);
        if(factureOptional.isEmpty()) {
            throw new InvoiceApiException("Facture with id " + id + " does not exist");
        }

        Facture facture = factureOptional.get();
        facture.setStatus(newStatus);
        factureRepository.save(facture);

        return facture;
    }
}
