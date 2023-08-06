package com.dyinvoice.backend.repository;


import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByNameAndEntreprise(String name, Entreprise entreprise);

}
