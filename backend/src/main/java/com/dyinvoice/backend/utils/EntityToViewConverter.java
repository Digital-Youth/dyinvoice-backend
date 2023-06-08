package com.dyinvoice.backend.utils;

import com.dyinvoice.backend.model.entity.AppUser;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.view.AppUserView;
import org.slf4j.LoggerFactory;
import org.springframework.data.convert.EntityConverter;

import java.util.logging.Logger;

public class EntityToViewConverter {



    private static final Logger log = (Logger) LoggerFactory.getLogger(EntityConverter.class);

    public static AppUserView convertEntityToAppUserView(AppUser savedAppUser) {

        AppUserView view = new AppUserView();

        view.setId(savedAppUser.getId());
        view.setEmail(savedAppUser.getEmail());
        view.setFirstName(savedAppUser.getFirstName());
        view.setLastName(savedAppUser.getLastName());
        view.setPhoneNumber(savedAppUser.getPhoneNumber());
        view.setEntreprise(savedAppUser.getEntreprise());
        return view;
    }
}
