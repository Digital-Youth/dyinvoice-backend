package com.dyinvoice.backend.dao;

import com.dyinvoice.backend.exception.ResourceNotFoundException;
import com.dyinvoice.backend.model.entity.AppUser;

public interface InvitationDAO {

    String createInvitation(AppUser AppUser, String inviteeEmail) throws ResourceNotFoundException;
}
