package com.dyinvoice.backend.service;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.InvitationForm;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.view.AppUserView;

import java.util.Optional;

public interface AppUserService {

    AppUserView getAppUserInfoById(AppUserForm form) throws ValidationException, ResourceNotFoundException;

    AppUserView getUserInfo(String token) throws ValidationException, ResourceNotFoundException;

    AppUser registerUser(RegisterForm registerForm) throws ValidationException, ResourceNotFoundException;

    String loginUser(LoginForm loginForm) throws ValidationException, ResourceNotFoundException;

    AppUser updateAppUser(AppUserForm form) throws ValidationException, ResourceNotFoundException;

    String createInvitation(InvitationForm form) throws ResourceNotFoundException, ValidationException;

    AppUser getAppUserById(Long id);
}
