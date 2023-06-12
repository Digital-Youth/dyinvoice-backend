package com.dyinvoice.backend.dao;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.view.AppUserView;

public interface AppUserDAO {

    boolean isUserExist(AppUser appUser);

    AppUserView getAppUserInfo(AppUser appUser) throws ResourceNotFoundException;

    String login(LoginForm loginForm);

    String register(AppUser appUser);

    AppUser getAppUser(AppUser appUser) throws ResourceNotFoundException;

    AppUser updateAppUser(AppUser appUser) throws ResourceNotFoundException;

}
