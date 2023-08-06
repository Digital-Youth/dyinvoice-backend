package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Prestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestationRepository extends JpaRepository<Prestation, Long> {

    Optional<Prestation> getPrestationByName(String name);

    Optional<Prestation> findByName(String name);

    Optional<Prestation> findByNameAndEntreprise(String name, Entreprise entreprise);
}
