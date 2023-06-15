package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.exception.ExceptionType;
import com.dyinvoice.backend.exception.InvoiceApiException;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.entity.Role;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.repository.AppUserRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.repository.RoleRepository;
import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.utils.EntityToViewConverter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@AllArgsConstructor
@Component
public class AppUserDAOImpl implements AppUserDAO {

    AppUserRepository appUserRepository;
    AuthenticationManager authenticationManager;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;

    EntrepriseRepository enterpriseRepository;

    @Override
    public boolean isUserExist(AppUser appUser) {
        Optional<AppUser> appUserEntity = appUserRepository.findById(appUser.getId());

        return appUserEntity.isPresent();
    }

    @Override
    public AppUser getAppUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public AppUserView getAppUserInfo(AppUser appUser) throws ResourceNotFoundException {
        return EntityToViewConverter.convertEntityToAppUserView(getAppUser(appUser));
    }

    @Override
    public AppUser getAppUser(AppUser appUser) throws ResourceNotFoundException {

        AppUser appUserEntity = null;

        if(appUser.getId()  != null) {
            appUserEntity = appUserRepository.findById(appUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(ExceptionType.ERROR_MSG_USER_PROFILE_NOT_FOUND));
        }else if(appUser.getEmail()!= null) {
            appUserEntity = appUserRepository.findByEmail(appUser.getEmail());

            if(appUserEntity == null){
                throw  new ResourceNotFoundException(ExceptionType.ERROR_MSG_USER_PROFILE_NOT_FOUND);
            }
        }
        return appUserEntity;
    }

    @Override
    public String login(AppUser appUser) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                appUser.getEmail(), appUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(AppUser appUser) {

                // Check if the user already exists
                AppUser existingUser = appUserRepository.findByEmail(appUser.getEmail());
                if (existingUser != null) {
                    throw new InvoiceApiException(HttpStatus.BAD_REQUEST, "User with this email already exists");
                }

                existingUser = appUserRepository.findByPhoneNumber(appUser.getPhoneNumber());
                if (existingUser != null) {
                    throw new InvoiceApiException(HttpStatus.BAD_REQUEST, "User with this phone number already exists");
                }

                // Check if a company with this SIRET already exists
                Entreprise existingCompany = enterpriseRepository.findBySiret(appUser.getEntreprise().getSiret());
                if (existingCompany != null) {
                    throw new InvoiceApiException(HttpStatus.BAD_REQUEST, "Company with this SIRET already exists");
                }

                appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

                // Create the company
                Entreprise company = createEntreprise(appUser);
                appUser.setEntreprise(company);

                // Assign roles to the user
                Set<Role> roles = new HashSet<>();
                Optional<Role> userRoleOptional = roleRepository.findByName(EntitiesRoleName.ROLE_ADMIN);
                if (userRoleOptional.isEmpty()) {
                    throw new InvoiceApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Admin role not found");
                }
                roles.add(userRoleOptional.get());

                appUser.setRoles(roles);
                appUserRepository.save(appUser);

                return "User Created successfully";

    }



    public Entreprise createEntreprise(AppUser appUser) {

        //Create a Company for a new User
        Entreprise company = new Entreprise();

        Entreprise appUserCompany =  appUser.getEntreprise();

        if(appUserCompany == null) {
            throw new IllegalArgumentException("User must have a company");
        }

        company.setName(appUserCompany.getName());

        company.setSiret(appUserCompany.getSiret());
        company.setAppUser(appUser);

        company = enterpriseRepository.save(company);

        return company;
    }

    @Override
    public AppUser updateAppUser(AppUser appUser) throws ResourceNotFoundException {
        return null;
    }
}
