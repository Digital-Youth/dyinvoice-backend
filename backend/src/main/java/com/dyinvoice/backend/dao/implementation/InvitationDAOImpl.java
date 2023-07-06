package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.config.EmailSender;
import com.dyinvoice.backend.dao.InvitationDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Invitations;
import com.dyinvoice.backend.model.entity.Role;
import com.dyinvoice.backend.repository.AppUserRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.repository.InvitationRepository;
import com.dyinvoice.backend.repository.RoleRepository;
import com.dyinvoice.backend.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@Component
public class InvitationDAOImpl implements InvitationDAO {

    private static final Logger logger = LoggerFactory.getLogger(ClientDAOImpl.class);

    AppUserRepository appUserRepository;
    AuthenticationManager authenticationManager;
    RoleRepository roleRepository;
    InvitationRepository invitationRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;
    EmailSender emailSender;
    EntrepriseRepository enterpriseRepository;
    private final Environment environment;
    TemplateEngine templateEngine;




    private String generateRandomPassword() {
        return UUID.randomUUID().toString();
    }
    @Override
    @Transactional
    public String createInvitation(AppUser appUser, String inviteeEmail) throws ResourceNotFoundException {
        try {
            String randomPassword = generateRandomPassword();

            AppUser invitedUser = new AppUser();
            invitedUser.setEmail(inviteeEmail);
            invitedUser.setEntreprise(appUser.getEntreprise());
            invitedUser.setPassword(passwordEncoder.encode(randomPassword));
            invitedUser.setEnabled(false); // User is not enabled until invitation is accepted

            // Save the AppUser in the AppUser table
            AppUser savedInvitedUser = appUserRepository.save(invitedUser);

            // Set default role to STAFF
            Optional<Role> staffRole = roleRepository.findByName(EntitiesRoleName.ROLE_STAFF);
            if(staffRole.isPresent()) {
                Set<Role> roles = new HashSet<>();
                roles.add(staffRole.get());
                savedInvitedUser.setRoles(roles);
                appUserRepository.save(savedInvitedUser);
            } else {
                throw new ResourceNotFoundException("Role STAFF not found");
            }

            Invitations newInvitations = new Invitations();
            String token = UUID.randomUUID().toString();
            newInvitations.setToken(token);
            newInvitations.setEmail(savedInvitedUser.getEmail());
            newInvitations.setSenderEmail(appUser.getEmail());
            newInvitations.setExpiryDate(LocalDateTime.now().plusDays(7));
            newInvitations.setEntreprise(appUser.getEntreprise());

            newInvitations.setAppUser(savedInvitedUser);

            Invitations savedInvitation = invitationRepository.save(newInvitations);

            // Try to send the email
            try {
                sendInvitation(savedInvitation, token);
                savedInvitation.setEmailSent(true);
                invitationRepository.save(savedInvitation);
            } catch (MailException e) {
                // Log the error, and keep 'emailSent' as false
                logger.error("Failed to send invitation: " + e.getMessage());
            }

            return "Invitation for " + savedInvitation.getEmail() + " created.";
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error creating invitation: " + e.getMessage());
        }
    }


    public void sendInvitation(Invitations invitation, String token) throws Exception {
        String fromEmail = environment.getProperty("spring.mail.properties.mail.smtp.from", "noreply@jterx.com");
        String linkText = "Join Your Team";
        String invitationUrl = "http://yourwebsite.com/accept-invitation?token=" + token;

        String companyName = "unknown company";
        if (invitation.getEntreprise() != null) {
            companyName = invitation.getEntreprise().getName();
        }

        String subject = "Join the " + companyName +" team on Invoice!";
        String body = "Hello from Invoices\n " +
                " you've been invited to join " + companyName + "." + "Use the link below to set up your account and get started: <br/>"
                + "<a href=\"" + invitationUrl + "\">" + linkText + "</a>"
                + "<br/>After accepting the invitation, please reset your password. <br/>"
                + "\n Thanks Invoice Team";

        emailSender.sendEmail(fromEmail, invitation.getEmail(), subject, body);
    }


    public void acceptInvitation(String token) throws ResourceNotFoundException {
        Optional<Invitations> invitationOpt = invitationRepository.findByToken(token);

        if (invitationOpt.isEmpty()) {
            throw new ResourceNotFoundException("Invitation not found for token: " + token);
        }

        Invitations invitation = invitationOpt.get();

        // Check if the invitation has expired
        if (invitation.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ResourceNotFoundException("Invitation expired for token: " + token);
        }

        String userEmail = invitation.getEmail();
        AppUser user = appUserRepository.findByEmail(userEmail);

        if (user == null) {
            throw new ResourceNotFoundException("User not found for email: " + userEmail);
        }

        // Enable the user
        user.setEnabled(true);
        appUserRepository.save(user);
    }


}
