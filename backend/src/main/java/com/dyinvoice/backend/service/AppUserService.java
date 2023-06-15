package com.dyinvoice.backend.service;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.view.AppUserView;

public interface AppUserService {

    AppUserView getAppUserInfo(AppUserForm form) throws ValidationException, ResourceNotFoundException;

    AppUser registerUser(RegisterForm registerForm) throws ValidationException, ResourceNotFoundException;

    String loginUser(LoginForm loginForm) throws ValidationException, ResourceNotFoundException;
}
