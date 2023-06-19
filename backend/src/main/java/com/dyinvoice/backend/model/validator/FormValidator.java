package com.dyinvoice.backend.model.validator;

import com.dyinvoice.backend.exception.ExceptionType;
import com.dyinvoice.backend.model.form.AppUserForm;
import com.dyinvoice.backend.model.form.LoginForm;
import com.dyinvoice.backend.model.form.RegisterForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormValidator {

    public static List<String> validateAppUserReadForm(AppUserForm form) {
        List<String> errorList = new ArrayList<String>();
        if (form.getId() == null && (form.getEmail() == null || form.getEmail().isEmpty())) {
            errorList.add("Either User ID or email is required");
        }
        return errorList;
    }


    public static List<String> validateRegisterForm(RegisterForm form) {
        List<String> errorList = new ArrayList<>();

        if(form.getEmail() == null || !form.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorList.add("Invalid or null email.");
        }

        if(form.getPassword() == null || !form.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
            errorList.add("Password must be at least 8 characters long and contain at least one number, one uppercase letter, and one special character.");
        }

        if(form.getPhoneNumber() == null || !form.getPhoneNumber().matches("\\d{10}")) {
            errorList.add("Invalid or null phone number.");
        }

        return errorList;
    }

    public static List<String> validateLoginForm(LoginForm form) {
        List<String> errorList = new ArrayList<>();

        if(form.getEmail() == null || !form.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorList.add("Invalid or null email.");
        }

        if(form.getPassword() == null || form.getPassword().trim().isEmpty()) {
            errorList.add("Invalid or null password.");
        }

        return errorList;
    }


    public static String getErrorMessages(List<String> errorList) {
        return errorList.stream().collect(Collectors.joining(ExceptionType.SEMI_COLON + ExceptionType.SPACE));
    }
}


