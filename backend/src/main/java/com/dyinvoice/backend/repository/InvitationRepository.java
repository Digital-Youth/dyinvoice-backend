package com.dyinvoice.backend.repository;

import com.dyinvoice.backend.model.entity.Invitations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitations, Long> {

    List<Invitations> findByEmailSent(boolean emailSent);
    Optional<Invitations> findByToken(String token);


}
