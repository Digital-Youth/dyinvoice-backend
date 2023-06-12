package com.dyinvoice.backend.dao.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.exception.ExceptionType;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.entity.Entreprise;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.repository.AppUserRepository;
import com.dyinvoice.backend.repository.EntrepriseRepository;
import com.dyinvoice.backend.repository.RoleRepository;
import com.dyinvoice.backend.security.JwtTokenProvider;
import com.dyinvoice.backend.utils.EntityToViewConverter;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


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
    public String login(LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.getEmail(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(AppUser appUser,  RegisterForm registerForm) {
        try {


        }catch (Exception e) {

        }
        return null;
    }

    public Entreprise createEntreprise(AppUser appUser) {

        //Create a Company for a new User
        Entreprise company = new Entreprise();
        company.setName(company.getName());
        company.setAddress(company.getAddress());
        company.setAppUser(appUser);

        company = enterpriseRepository.save(company);

        return company;
    }

    @Override
    public AppUser createAppUser(AppUser appUser) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public AppUser updateAppUser(AppUser appUser) throws ResourceNotFoundException {
        return null;
    }
}
