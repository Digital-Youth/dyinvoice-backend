package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findById(String id);
    AppUser findByEmail(String email);
}
