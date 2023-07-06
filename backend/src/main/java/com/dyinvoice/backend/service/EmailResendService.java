package com.dyinvoice.backend.service;


import com.dyinvoice.backend.dao.ClientDAO;
import com.dyinvoice.backend.dao.InvitationDAO;
import com.dyinvoice.backend.dao.implementation.InvitationDAOImpl;
import com.dyinvoice.backend.model.entity.Invitations;
import com.dyinvoice.backend.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailResendService {


    private final InvitationRepository invitationRepository;

    private final InvitationDAOImpl invitationDAO;

    @Autowired
    public EmailResendService(InvitationRepository invitationRepository, InvitationDAOImpl invitationDAO) {
        this.invitationRepository = invitationRepository;
        this.invitationDAO = invitationDAO;
    }



    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void resendEmails() {
        List<Invitations> failedInvitations = invitationRepository.findByEmailSent(false);

        for (Invitations invitation : failedInvitations) {
            try {
                String newToken = UUID.randomUUID().toString();
                invitation.setToken(newToken);
                invitation = invitationRepository.save(invitation);
                invitationDAO.sendInvitation(invitation, newToken);
                invitation.setEmailSent(true);
                invitationRepository.save(invitation);
            } catch (MailException e) {
                System.err.println("Failed to resend invitation: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
