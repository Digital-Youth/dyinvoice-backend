package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

    Entreprise findByAppUser(AppUser appUser);
    Entreprise findBySiret(String siret);

}
