package com.dyinvoice.backend.service;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.exception.ValidationException;
import com.dyinvoice.backend.model.entity.Client;
import com.dyinvoice.backend.model.form.ClientForm;

public interface ClientService {

    Client createClient(ClientForm form) throws ValidationException, ResourceNotFoundException;

}
