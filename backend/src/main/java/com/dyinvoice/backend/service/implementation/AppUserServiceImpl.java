package com.dyinvoice.backend.service.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.dao.InvitationDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.InvitationForm;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.service.AppUserService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserDAO appUserDAO;

    private InvitationDAO invitationDAO;


    @Autowired
    public AppUserServiceImpl(AppUserDAO appUserDAO, InvitationDAO invitationDAO) {
        this.appUserDAO = appUserDAO;
        this.invitationDAO = invitationDAO;
    }


    @Override
    public AppUserView getAppUserInfo(AppUserForm form) throws ValidationException, ResourceNotFoundException {
        List<String> errorList = FormValidator.validateAppUserReadForm(form);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        AppUser appUser;
        if (form.getId() != null) {
            appUser = appUserDAO.getAppUserById(form.getId());
        } else {
            appUser = appUserDAO.getAppUserByEmail(form.getEmail());
        }

        return appUserDAO.getAppUserInfo(appUser);
    }


    @Override
    public AppUser registerUser(RegisterForm registerForm) throws ValidationException, ResourceNotFoundException {

        List<String> errorList = FormValidator.validateRegisterForm(registerForm);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        AppUser appUser = FormToEntityConverter.convertRegisterFormToAppUser(registerForm);
        appUserDAO.register(appUser); // This line saves the AppUser object.
        return appUser;
    }

    @Override
    public String loginUser(LoginForm loginForm) throws ValidationException, ResourceNotFoundException {
        List<String> errorList = FormValidator.validateLoginForm(loginForm);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        AppUser appUser = new AppUser();
        appUser.setEmail(loginForm.getEmail());
        appUser.setPassword(loginForm.getPassword());

        // Return the JWT token after successful authentication
        return appUserDAO.login(appUser);
    }

    @Override
    public AppUser updateAppUser(AppUserForm form) throws ValidationException, ResourceNotFoundException {
        List<String> errorList = FormValidator.validateAppUserForm(form);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        AppUser existingAppUser = appUserDAO.getAppUserById(form.getId());
        if (existingAppUser == null) {
            throw new ResourceNotFoundException("AppUser not found with id : " + form.getId());
        }

        // Update the AppUser and Enterprise details from the form
        FormToEntityConverter.updateAppUserFromForm(form, existingAppUser);
        appUserDAO.updateAppUser(existingAppUser);

        return existingAppUser;
    }

    @Override
    public String createInvitation(InvitationForm form) throws ResourceNotFoundException, ValidationException {

        List<String> errorList = FormValidator.validateInvitationForm(form);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        // Get the user details
        AppUser existingAppUser = appUserDAO.getAppUserById(form.getId());
        if (existingAppUser == null) {
            throw new ResourceNotFoundException("AppUser not found with id : " + form.getId());
        }
        String inviteeEmail = form.getEmail();
        // Create the invitation
        return invitationDAO.createInvitation(existingAppUser, inviteeEmail);

    }

    @Override
    public AppUser getAppUserById(Long id) {
        return appUserDAO.getAppUserById(id);
    }


}
