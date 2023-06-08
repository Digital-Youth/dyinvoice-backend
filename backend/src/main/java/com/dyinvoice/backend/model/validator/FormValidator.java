package com.dyinvoice.backend.model.validator;

import com.dyinvoice.backend.exception.ExceptionType;
import com.dyinvoice.backend.model.form.AppUserForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormValidator {

    public static List<String> validateAppUserReadForm(AppUserForm form) {

        List<String> errorList = new ArrayList<String>();

        // required fields
        if (!Optional.ofNullable(form.getId()).isPresent()) {
            errorList.add(ExceptionType.ERROR_MSG_USER_ID_REQUIRED);
        }

        return errorList;
    }

    public static String getErrorMessages(List<String> errorList) {
        return errorList.stream().collect(Collectors.joining(ExceptionType.SEMI_COLON + ExceptionType.SPACE));
    }
}


