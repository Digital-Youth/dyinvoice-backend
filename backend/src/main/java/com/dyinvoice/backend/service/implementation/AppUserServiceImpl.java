package com.dyinvoice.backend.service.implementation;

import com.dyinvoice.backend.dao.AppUserDAO;
import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.RegisterForm;
import com.dyinvoice.backend.model.validator.FormValidator;
import com.dyinvoice.backend.model.view.AppUserView;
import com.dyinvoice.backend.service.AppUserService;
import com.dyinvoice.backend.utils.FormToEntityConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserDAO appUserDAO;


    @Override
    public AppUserView getAppUserInfo(AppUserForm form) throws ValidationException, ResourceNotFoundException {

        List<String> errorList = FormValidator.validateAppUserReadForm(form);
        if(errorList.size() > 0) {
            throw new ValidationException(FormValidator.getErrorMessages(errorList));
        }

        return appUserDAO.getAppUserInfo(FormToEntityConverter.convertFormToAppUser(form));
    }
}
