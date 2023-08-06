package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    @Query("select max(f.id) from Facture f")
    Long findMaxId();

    Optional<Facture> findByNumFacture(String numFacture);

    boolean existsByNumFacture(String numFacture);
}
