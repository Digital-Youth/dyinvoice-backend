package com.dyinvoice.backend.dao;


import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.view.AppUserView;

import java.util.Optional;

public interface AppUserDAO {

    boolean isUserExist(AppUser appUser);

    Optional<AppUser> getUserInfo(String token);

    AppUser getAppUserByEmail(String email);

    AppUser getAppUserById(Long id);

    AppUserView getAppUserInfoById(AppUser appUser) throws ResourceNotFoundException;

    String login(AppUser AppUser) throws ResourceNotFoundException;

    String register(AppUser appUser);

    AppUser getAppUser(AppUser appUser) throws ResourceNotFoundException;

    AppUser updateAppUser(AppUser appUser) throws ResourceNotFoundException;



}
