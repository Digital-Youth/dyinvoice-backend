package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> getProductByName(String name);
    Optional<Product> findByName(String name);
    Optional<Product> findByNameAndEntreprise(String name, Entreprise entreprise);


}
